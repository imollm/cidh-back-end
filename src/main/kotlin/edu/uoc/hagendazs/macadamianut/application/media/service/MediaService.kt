package edu.uoc.hagendazs.macadamianut.application.media.service

import edu.uoc.hagendazs.macadamianut.application.event.event.model.dataClass.CIDHEvent
import edu.uoc.hagendazs.macadamianut.application.media.model.dataClass.UserEventComment
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.MNUser

interface MediaService {
    fun addToFavorites(event: CIDHEvent, user: MNUser)
    fun removeFromFavorites(event: CIDHEvent, user: MNUser)
    fun favoriteEventsForUser(user: MNUser): Collection<CIDHEvent>
    fun rateEvent(event: CIDHEvent, user: MNUser, rating: Int)
    fun attendEvent(event: CIDHEvent, user: MNUser)
    fun postComment(event: CIDHEvent, comment: String, user: MNUser)
    fun commentsForEvent(event: CIDHEvent): Collection<UserEventComment>
}