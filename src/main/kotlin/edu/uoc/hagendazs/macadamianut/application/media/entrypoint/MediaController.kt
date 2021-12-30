package edu.uoc.hagendazs.macadamianut.application.media.entrypoint

import edu.uoc.hagendazs.macadamianut.application.event.event.model.CIDHEvent
import edu.uoc.hagendazs.macadamianut.application.event.event.service.EventService
import edu.uoc.hagendazs.macadamianut.application.media.entrypoint.input.EventCommentRequest
import edu.uoc.hagendazs.macadamianut.application.media.entrypoint.input.PostForumMessageRequest
import edu.uoc.hagendazs.macadamianut.application.media.entrypoint.output.EventCommentResponse
import edu.uoc.hagendazs.macadamianut.application.media.entrypoint.output.ForumResponse
import edu.uoc.hagendazs.macadamianut.application.media.entrypoint.output.toEventCommentResponse
import edu.uoc.hagendazs.macadamianut.application.media.service.MediaService
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.MNUser
import edu.uoc.hagendazs.macadamianut.application.user.service.UserService
import edu.uoc.hagendazs.macadamianut.application.user.utils.UserUtils
import edu.uoc.hagendazs.macadamianut.common.HTTPMessages
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@Controller
@RequestMapping("/api/v1")
class MediaController {

    @Autowired
    private lateinit var mediaService: MediaService

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var eventService: EventService

    @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN', 'USER')")
    @PostMapping(value = ["/events/{eventId}/add-to-favorites"])
    fun addEventToFavorites(
        @PathVariable("eventId") eventId: String,
        jwtToken: Authentication
    ): ResponseEntity<Void> {
        val (event, user) = this.findUserAndEventOrThrow(eventId, jwtToken.name)

        mediaService.addToFavorites(event, user)
        return ResponseEntity.ok().build()
    }

    @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN', 'USER')")
    @PostMapping(value = ["/events/{eventId}/remove-from-favorites"])
    fun removeFromFavorites(
        @PathVariable("eventId") eventId: String,
        jwtToken: Authentication
    ): ResponseEntity<Void> {
        val (event, user) = this.findUserAndEventOrThrow(eventId, jwtToken.name)

        mediaService.removeFromFavorites(event, user)
        return ResponseEntity.ok().build()
    }

    @PreAuthorize("#userId == authentication.principal.claims['username'] " +
            "or hasAnyRole('ADMIN', 'SUPERADMIN') or #userId == null")
    @GetMapping(value = ["/users/{userId}/favorite-events", "/users/me/favorite-events"])
    fun getUserFavorites(
        @PathVariable userId: String?,
        jwtToken: Authentication
    ): ResponseEntity<Collection<CIDHEvent>> {
        val resolvedUserId = UserUtils.resolveUserId(userId, jwtToken)
        val user = userService.findUserById(resolvedUserId) ?: run {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, HTTPMessages.USER_NOT_FOUND)
        }
        val favEvents = mediaService.favoriteEventsForUser(user)

        return ResponseEntity.ok(favEvents)
    }

    @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN', 'USER')")
    @PostMapping(value = ["/events/{eventId}/rate"])
    fun rateEvent(
        @PathVariable("eventId") eventId: String,
        @RequestParam("rating") rating: Int,
        jwtToken: Authentication
    ): ResponseEntity<Void> {
        require((1..5).contains(rating)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Rating should be between 1 and 5. " +
                    "Provided rating -> $rating")
        }
        val (event, user) = this.findUserAndEventOrThrow(eventId, jwtToken.name)

        mediaService.rateEvent(event, user, rating)
        return ResponseEntity.ok().build()
    }

    @PostMapping(value = ["/events/{eventId}/subscribe"])
    fun subscribeToEvent(
        @PathVariable("eventId") eventId: String,
        jwtToken: Authentication,
    ): ResponseEntity<Void> {
        val (event, user) = this.findUserAndEventOrThrow(eventId, jwtToken.name)

        mediaService.subscribeToAnEvent(event, user)
        return ResponseEntity.ok().build()
    }

    @PostMapping(value = ["/events/{eventId}/unsubscribe"])
    fun unsubscribeToEvent(
        @PathVariable("eventId") eventId: String,
        jwtToken: Authentication,
    ): ResponseEntity<Void> {
        val (event, user) = this.findUserAndEventOrThrow(eventId, jwtToken.name)

        mediaService.unsubscribeToAnEvent(event, user)
        return ResponseEntity.ok().build()
    }

    @PostMapping(value = ["/events/{eventId}/post-comment"])
    fun postCommentToEvent(
        @PathVariable eventId: String,
        @RequestBody comment: EventCommentRequest,
        jwtToken: Authentication,
    ): ResponseEntity<Void> {
        val (event, user) = this.findUserAndEventOrThrow(eventId, jwtToken.name)

        mediaService.postComment(event, comment.comment, user, comment.createdAt)
        return ResponseEntity.ok().build()
    }

    @GetMapping(value = ["/events/{eventId}/comments"])
    fun getEventComments(
        @PathVariable eventId: String,
    ): ResponseEntity<Collection<EventCommentResponse>> {
        val event = getEventOrThrow(eventId)
        val eventComments = mediaService.commentsForEvent(event).map { it.toEventCommentResponse() }

        return ResponseEntity.ok(eventComments)
    }

    @PreAuthorize ("#forumMessageReq.parentMessageId == null or hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PostMapping(value = ["/events/{eventId}/forum/post-message"])
    fun postMessageInForum(
        @PathVariable eventId: String,
        @RequestBody forumMessageReq: PostForumMessageRequest,
        jwtToken: Authentication,
    ): ResponseEntity<Void> {
        val (event, user) = this.findUserAndEventOrThrow(eventId, jwtToken.name)
        mediaService.postForumMessage(event, user, forumMessageReq)
        return ResponseEntity.ok().build()
    }

    @GetMapping(value = ["/events/{eventId}/forum"])
    fun getEventForum(
        @PathVariable eventId: String
    ):ResponseEntity<ForumResponse> {
        val event = getEventOrThrow(eventId)

        val forum = mediaService.getForumForEvent(event)

        return ResponseEntity.ok(forum)
    }

    // ########################################################
    // ################### Internal methods ###################
    // ########################################################

    private fun findUserAndEventOrThrow(
        eventId: String,
        userId: String
    ): Pair<CIDHEvent, MNUser> {
        val event = getEventOrThrow(eventId)
        val user = getUserOrThrow(userId)
        return Pair(event, user)
    }

    private fun getUserOrThrow(userId: String) = userService.findUserById(userId) ?: run {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, HTTPMessages.USER_NOT_FOUND)
    }

    private fun getEventOrThrow(eventId: String) = eventService.findById(eventId, null) ?: run {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, HTTPMessages.NOT_FOUND)
    }
}