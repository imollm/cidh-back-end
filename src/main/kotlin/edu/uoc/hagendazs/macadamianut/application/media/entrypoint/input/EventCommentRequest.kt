package edu.uoc.hagendazs.macadamianut.application.media.entrypoint.input

import java.time.LocalDateTime

data class EventCommentRequest(
    val comment: String,
    val createdAt: LocalDateTime,
)


