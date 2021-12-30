package edu.uoc.hagendazs.macadamianut.application.media.model

import edu.uoc.hagendazs.macadamianut.application.event.event.entrypoint.output.EventResponse
import edu.uoc.hagendazs.macadamianut.application.event.event.model.CIDHEvent
import edu.uoc.hagendazs.macadamianut.application.media.entrypoint.input.PostForumMessageRequest
import edu.uoc.hagendazs.macadamianut.application.media.entrypoint.output.ForumMessage
import edu.uoc.hagendazs.macadamianut.application.media.model.dataClass.EventRating
import edu.uoc.hagendazs.macadamianut.application.media.model.dataClass.UserEventComment
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.MNUser
import java.time.LocalDateTime

interface MediaRepo {
    fun ratingForEvent(eventId: String?): EventRating?
    fun addToFavorites(event: CIDHEvent, user: MNUser)
    fun removeFromFavorites(event: CIDHEvent, user: MNUser)
    fun favoriteEventsForUser(user: MNUser): Collection<EventResponse>
    fun saveOrUpdateRatingForEvent(event: CIDHEvent, user: MNUser, rating: Int)
    fun saveUserSubscribesToAnEvent(attendee: MNUser, attendedEvent: CIDHEvent)
    fun saveCommentForEvent(comment: String, event: CIDHEvent, author: MNUser, createdAt: LocalDateTime)
    fun commentsForEvent(event: CIDHEvent): Collection<UserEventComment>
    fun saveForumMessageForEvent(event: CIDHEvent, user: MNUser, forumMessageReq: PostForumMessageRequest)
    fun getForumMessagesForEvent(eventId: String): Collection<ForumMessage>
    fun isFavoriteEventForUserId(eventId: String, requesterUserId: String?): Boolean
    fun unsubscribeUserFromEvent(user: MNUser, event: CIDHEvent)
    fun isUserSubscribedToEvent(eventId: String, requesterUserId: String?): Boolean
    fun userRatingForEvent(eventId: String, requesterUserId: String?): Number?
    fun hasUserAlreadyCommentedEvent(eventId: String, userId: String): Boolean
}
