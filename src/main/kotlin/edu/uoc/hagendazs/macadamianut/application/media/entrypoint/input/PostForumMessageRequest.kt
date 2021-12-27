package edu.uoc.hagendazs.macadamianut.application.media.entrypoint.input

import java.time.LocalDateTime

data class PostForumMessageRequest(
    val message: String,
    val parentMessageId: String?,
    val createdAt: LocalDateTime,
)
