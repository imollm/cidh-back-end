package edu.uoc.hagendazs.macadamianut.application.media.entrypoint.output

import java.time.LocalDateTime

data class ForumResponse(
    val eventId: String,
    val eventName: String,
    val messages: Collection<ForumMessage>,
)

data class ForumMessage(
    val id: String,
    val authorUserId: String,
    val authorFirstName: String,
    val createdAt: LocalDateTime,
    val parentMessageId: String?,
    val message: String,
)
