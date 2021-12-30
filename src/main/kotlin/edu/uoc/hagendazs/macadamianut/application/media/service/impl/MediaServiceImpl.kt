package edu.uoc.hagendazs.macadamianut.application.media.service.impl

import edu.uoc.hagendazs.macadamianut.application.event.event.entrypoint.output.EventResponse
import edu.uoc.hagendazs.macadamianut.application.event.event.model.CIDHEvent
import edu.uoc.hagendazs.macadamianut.application.media.entrypoint.input.PostForumMessageRequest
import edu.uoc.hagendazs.macadamianut.application.media.entrypoint.output.ForumResponse
import edu.uoc.hagendazs.macadamianut.application.media.model.MediaRepo
import edu.uoc.hagendazs.macadamianut.application.media.model.dataClass.EventRating
import edu.uoc.hagendazs.macadamianut.application.media.model.dataClass.UserEventComment
import edu.uoc.hagendazs.macadamianut.application.media.service.MediaService
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.MNUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class MediaServiceImpl: MediaService {

    @Autowired
    private lateinit var mediaRepo: MediaRepo


    override fun addToFavorites(event: CIDHEvent, user: MNUser) {
        mediaRepo.addToFavorites(event, user)
    }

    override fun removeFromFavorites(event: CIDHEvent, user: MNUser) {
        mediaRepo.removeFromFavorites(event, user)
    }

    override fun favoriteEventsForUser(user: MNUser): Collection<EventResponse> {
        return mediaRepo.favoriteEventsForUser(user)
    }

    override fun rateEvent(event: CIDHEvent, user: MNUser, rating: Int) {
        return mediaRepo.saveOrUpdateRatingForEvent(event, user, rating)
    }

    override fun subscribeToAnEvent(event: CIDHEvent, user: MNUser) {
        return mediaRepo.saveUserSubscribesToAnEvent(user, event)
    }

    override fun unsubscribeToAnEvent(event: CIDHEvent, user: MNUser) {
        mediaRepo.unsubscribeUserFromEvent(user, event)
    }

    override fun postComment(event: CIDHEvent, comment: String, user: MNUser, createdAt: LocalDateTime) {
        mediaRepo.saveCommentForEvent(comment, event, user, createdAt)
    }

    override fun commentsForEvent(event: CIDHEvent): Collection<UserEventComment> {
        return mediaRepo.commentsForEvent(event)
    }

    override fun ratingForEvent(eventId: String?): EventRating? {
        return mediaRepo.ratingForEvent(eventId)
    }

    override fun postForumMessage(event: CIDHEvent, user: MNUser, forumMessageReq: PostForumMessageRequest) {
        mediaRepo.saveForumMessageForEvent(event, user, forumMessageReq)
    }

    override fun getForumForEvent(event: EventResponse): ForumResponse {
        val messages = mediaRepo.getForumMessagesForEvent(event.id)
        return ForumResponse(
            eventId = event.id,
            eventName = event.name,
            messages = messages,
        )
    }
}
