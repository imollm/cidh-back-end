package edu.uoc.hagendazs.macadamianut.application.media.service

import edu.uoc.hagendazs.macadamianut.application.event.event.entrypoint.output.EventResponse
import edu.uoc.hagendazs.macadamianut.application.event.event.model.CIDHEvent
import edu.uoc.hagendazs.macadamianut.application.media.entrypoint.input.PostForumMessageRequest
import edu.uoc.hagendazs.macadamianut.application.media.entrypoint.output.ForumResponse
import edu.uoc.hagendazs.macadamianut.application.media.model.dataClass.EventRating
import edu.uoc.hagendazs.macadamianut.application.media.model.dataClass.UserEventComment
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.MNUser
import java.time.LocalDateTime

interface MediaService {
    fun addToFavorites(event: CIDHEvent, user: MNUser)
    fun removeFromFavorites(event: CIDHEvent, user: MNUser)
    fun favoriteEventsForUser(user: MNUser): Collection<EventResponse>
    fun rateEvent(event: CIDHEvent, user: MNUser, rating: Int)
    fun ratingForEvent(eventId: String?): EventRating?
    fun attendEvent(attendedEvent: CIDHEvent, attendee: MNUser)
    fun postComment(event: CIDHEvent, comment: String, user: MNUser, createdAt: LocalDateTime)
    fun commentsForEvent(event: CIDHEvent): Collection<UserEventComment>
    fun postForumMessage(event: CIDHEvent, user: MNUser, forumMessageReq: PostForumMessageRequest)
    fun getForumForEvent(event: EventResponse): ForumResponse
}