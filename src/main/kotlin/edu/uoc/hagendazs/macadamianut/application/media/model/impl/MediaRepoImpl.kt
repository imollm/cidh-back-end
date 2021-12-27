package edu.uoc.hagendazs.macadamianut.application.media.model.impl

import edu.uoc.hagendazs.macadamianut.application.event.event.entrypoint.output.EventResponse
import edu.uoc.hagendazs.macadamianut.application.event.event.model.CIDHEvent
import edu.uoc.hagendazs.macadamianut.application.media.entrypoint.input.PostForumMessageRequest
import edu.uoc.hagendazs.macadamianut.application.media.entrypoint.output.ForumMessage
import edu.uoc.hagendazs.macadamianut.application.media.model.MediaRepo
import edu.uoc.hagendazs.macadamianut.application.media.model.dataClass.UserEventComment
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.MNUser
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class MediaRepoImpl: MediaRepo {

    @Autowired
    private lateinit var dsl: DSLContext

    override fun ratingForEvent(eventId: String): Number {
        val ratingsForEvent = dsl.select()
    }

    override fun addToFavorites(event: CIDHEvent, user: MNUser) {
        TODO("Not yet implemented")
    }

    override fun removeFromFavorites(event: CIDHEvent, user: MNUser) {
        TODO("Not yet implemented")
    }

    override fun favoriteEventsForUser(user: MNUser): Collection<EventResponse> {
        TODO("Not yet implemented")
    }

    override fun saveRatingForEvent(event: CIDHEvent, user: MNUser, rating: Int) {
        TODO("Not yet implemented")
    }

    override fun saveUserAttendsToEvent(attendee: MNUser, attendedEvent: CIDHEvent) {
        TODO("Not yet implemented")
    }

    override fun saveCommentForEvent(comment: String, event: CIDHEvent, author: MNUser) {
        TODO("Not yet implemented")
    }

    override fun commentsForEvent(event: CIDHEvent): Collection<UserEventComment> {
        TODO("Not yet implemented")
    }

    override fun saveForumMessageForEvent(event: CIDHEvent, user: MNUser, forumMessageReq: PostForumMessageRequest) {
        TODO("Not yet implemented")
    }

    override fun getForumMessagesForEvent(eventId: String): Collection<ForumMessage> {
        TODO("Not yet implemented")
    }
}