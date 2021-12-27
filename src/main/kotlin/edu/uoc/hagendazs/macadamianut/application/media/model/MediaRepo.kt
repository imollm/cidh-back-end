package edu.uoc.hagendazs.macadamianut.application.media.model

import edu.uoc.hagendazs.macadamianut.application.event.event.entrypoint.output.EventResponse
import edu.uoc.hagendazs.macadamianut.application.event.event.model.CIDHEvent
import edu.uoc.hagendazs.macadamianut.application.media.entrypoint.input.PostForumMessageRequest
import edu.uoc.hagendazs.macadamianut.application.media.entrypoint.output.ForumMessage
import edu.uoc.hagendazs.macadamianut.application.media.model.dataClass.UserEventComment
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.MNUser

interface MediaRepo {
    fun ratingForEvent(eventId: String): Number
    fun addToFavorites(event: CIDHEvent, user: MNUser)
    fun removeFromFavorites(event: CIDHEvent, user: MNUser)
    fun favoriteEventsForUser(user: MNUser): Collection<EventResponse>
    fun saveRatingForEvent(event: CIDHEvent, user: MNUser, rating: Int)
    fun saveUserAttendsToEvent(attendee: MNUser, attendedEvent: CIDHEvent)
    fun saveCommentForEvent(comment: String, event: CIDHEvent, author: MNUser)
    fun commentsForEvent(event: CIDHEvent): Collection<UserEventComment>
    fun saveForumMessageForEvent(event: CIDHEvent, user: MNUser, forumMessageReq: PostForumMessageRequest)
    fun getForumMessagesForEvent(eventId: String): Collection<ForumMessage>
}
