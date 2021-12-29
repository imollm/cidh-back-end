package edu.uoc.hagendazs.macadamianut.application.media.model.impl

import edu.uoc.hagendazs.generated.jooq.tables.references.*
import edu.uoc.hagendazs.macadamianut.application.event.event.entrypoint.output.EventResponse
import edu.uoc.hagendazs.macadamianut.application.event.event.model.CIDHEvent
import edu.uoc.hagendazs.macadamianut.application.event.event.model.repo.EventRepo
import edu.uoc.hagendazs.macadamianut.application.media.entrypoint.input.PostForumMessageRequest
import edu.uoc.hagendazs.macadamianut.application.media.entrypoint.output.ForumMessage
import edu.uoc.hagendazs.macadamianut.application.media.model.MediaRepo
import edu.uoc.hagendazs.macadamianut.application.media.model.dataClass.EventRating
import edu.uoc.hagendazs.macadamianut.application.media.model.dataClass.UserEventComment
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.MNUser
import mu.KotlinLogging
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Repository
class MediaRepoImpl : MediaRepo {

    @Autowired
    private lateinit var dsl: DSLContext

    @Autowired
    @Lazy
    private lateinit var eventRepo: EventRepo

    private val logger = KotlinLogging.logger {}

    override fun ratingForEvent(eventId: String?): EventRating? {
        eventId ?: return null
        val ratingsForEvent = dsl.select(USER_EVENT_RATING.RATING)
            .from(USER_EVENT_RATING)
            .where(USER_EVENT_RATING.EVENT_ID.eq(eventId))
            .fetchInto(Int::class.java)

        if (ratingsForEvent.isEmpty()) {
            return EventRating(
                rating = 0,
                ratingsCount = 0,
                eventId = eventId
            )
        }
        val rating = ratingsForEvent.reduce { acc, rating -> acc + rating } / ratingsForEvent.size

        return EventRating(
            rating = rating,
            ratingsCount = ratingsForEvent.size,
            eventId = eventId,
        )
    }

    override fun addToFavorites(event: CIDHEvent, user: MNUser) {

        val favEventRelationAlreadyExists = dsl.fetchExists(
            dsl.selectFrom(USER_EVENT_FAVORITES)
                .where(USER_EVENT_FAVORITES.USER_ID.eq(user.id))
                .and(USER_EVENT_FAVORITES.EVENT_ID.eq(event.id))
        )

        if (favEventRelationAlreadyExists) {
            return
        }

        dsl.insertInto(USER_EVENT_FAVORITES)
            .set(USER_EVENT_FAVORITES.EVENT_ID, event.id)
            .set(USER_EVENT_FAVORITES.USER_ID, user.id)
            .set(USER_EVENT_FAVORITES.CREATED_AT, LocalDateTime.now())
            .execute()
    }

    override fun removeFromFavorites(event: CIDHEvent, user: MNUser) {
        //TODO: soft delete?
        dsl.deleteFrom(USER_EVENT_FAVORITES)
            .where(USER_EVENT_FAVORITES.USER_ID.eq(user.id))
            .and(USER_EVENT_FAVORITES.EVENT_ID.eq(event.id))
            .execute()
    }

    @Transactional
    override fun favoriteEventsForUser(user: MNUser): Collection<EventResponse> {
        val eventIds = dsl.select(USER_EVENT_FAVORITES.EVENT_ID)
            .from(USER_EVENT_FAVORITES)
            .where(USER_EVENT_FAVORITES.USER_ID.eq(user.id))
            .fetchInto(String::class.java)

        return eventIds.mapNotNull { eventRepo.findById(it, user.id) }
    }

    override fun saveOrUpdateRatingForEvent(event: CIDHEvent, user: MNUser, rating: Int) {

        val ratingExists = dsl.fetchExists(
            dsl.select(USER_EVENT_RATING.asterisk())
                .where(USER_EVENT_RATING.EVENT_ID.eq(event.id))
                .and(USER_EVENT_RATING.USER_ID.eq(user.id))
        )

        if (!ratingExists) {
            dsl.insertInto(USER_EVENT_RATING)
                .set(USER_EVENT_RATING.USER_ID, user.id)
                .set(USER_EVENT_RATING.EVENT_ID, event.id)
                .set(USER_EVENT_RATING.CREATED_AT, LocalDateTime.now())
                .set(USER_EVENT_RATING.RATING, rating)
                .execute()
        } else {
            dsl.update(USER_EVENT_RATING)
                .set(USER_EVENT_RATING.RATING, rating)
                .where(USER_EVENT_RATING.EVENT_ID.eq(event.id))
                .and(USER_EVENT_RATING.USER_ID.eq(user.id))
                .execute()
        }

    }

    override fun saveUserAttendsToEvent(attendee: MNUser, attendedEvent: CIDHEvent) {
        val alreadyAttended = dsl.fetchExists(
            dsl.select(USER_EVENT_ATTENDANCE.asterisk())
                .where(USER_EVENT_ATTENDANCE.USER_ID.eq(attendee.id))
                .and(USER_EVENT_ATTENDANCE.EVENT_ID.eq(attendedEvent.id))
        )

        if (alreadyAttended) {
            logger.info { "Attempted Attend to an event that the user already attended" }
            return
        }

        dsl.insertInto(USER_EVENT_ATTENDANCE)
            .set(USER_EVENT_ATTENDANCE.USER_ID, attendee.id)
            .set(USER_EVENT_ATTENDANCE.EVENT_ID, attendedEvent.id)
            .execute()
    }

    override fun saveCommentForEvent(comment: String, event: CIDHEvent, author: MNUser, createdAt: LocalDateTime) {
        val commentExists = dsl.fetchExists(
            dsl.select(USER_EVENT_COMMENT.asterisk())
                .from(USER_EVENT_COMMENT)
                .where(USER_EVENT_COMMENT.EVENT_ID.eq(event.id))
                .and(USER_EVENT_COMMENT.USER_ID.eq(author.id))
                .and(USER_EVENT_COMMENT.COMMENT.eq(comment))
                .and(USER_EVENT_COMMENT.CREATED_AT.eq(createdAt))
        )

        if (commentExists) {
            return
        }
        dsl.insertInto(USER_EVENT_COMMENT)
            .set(USER_EVENT_COMMENT.USER_ID, author.id)
            .set(USER_EVENT_COMMENT.EVENT_ID, event.id)
            .set(USER_EVENT_COMMENT.COMMENT, comment)
            .set(USER_EVENT_COMMENT.CREATED_AT, createdAt)
            .execute()
    }

    override fun commentsForEvent(event: CIDHEvent): Collection<UserEventComment> {
        return dsl.select(USER_EVENT_COMMENT.asterisk())
            .from(USER_EVENT_COMMENT)
            .where(USER_EVENT_COMMENT.EVENT_ID.eq(event.id))
            .fetchInto(UserEventComment::class.java)
    }

    override fun saveForumMessageForEvent(event: CIDHEvent,
                                          user: MNUser,
                                          forumMessageReq: PostForumMessageRequest) {

        val messageExists = dsl.fetchExists(
            dsl.selectFrom(EVENT_FORUM_MESSAGE)
                .where(EVENT_FORUM_MESSAGE.EVENT_ID.eq(event.id))
                .and(EVENT_FORUM_MESSAGE.AUTHOR_USER_ID.eq(user.id))
                .and(EVENT_FORUM_MESSAGE.CREATED_AT.eq(forumMessageReq.createdAt))
                .and(EVENT_FORUM_MESSAGE.MESSAGE.eq(forumMessageReq.message))
        )
        if (messageExists) {
            logger.info { "Attempted to insert a forum message that already exists" }
            return
        }
        dsl.insertInto(EVENT_FORUM_MESSAGE)
            .set(EVENT_FORUM_MESSAGE.ID, UUID.randomUUID().toString())
            .set(EVENT_FORUM_MESSAGE.EVENT_ID, event.id)
            .set(EVENT_FORUM_MESSAGE.MESSAGE, forumMessageReq.message)
            .set(EVENT_FORUM_MESSAGE.CREATED_AT, forumMessageReq.createdAt)
            .set(EVENT_FORUM_MESSAGE.AUTHOR_USER_ID, user.id)
            .set(EVENT_FORUM_MESSAGE.PARENT_ID, forumMessageReq.parentMessageId)
            .execute()
    }

    override fun getForumMessagesForEvent(eventId: String): Collection<ForumMessage> {
        return dsl.select(EVENT_FORUM_MESSAGE.asterisk())
            .from(EVENT_FORUM_MESSAGE)
            .where(EVENT_FORUM_MESSAGE.EVENT_ID.eq(eventId))
            .fetchInto(ForumMessage::class.java)
    }

    override fun isFavoriteEventForUserId(eventId: String, requesterUserId: String?): Boolean {
        requesterUserId ?: return false
        return dsl.fetchExists(
            dsl.selectFrom(USER_EVENT_FAVORITES)
                .where(USER_EVENT_FAVORITES.USER_ID.eq(requesterUserId))
                .and(USER_EVENT_FAVORITES.EVENT_ID.eq(eventId))
        )
    }
}