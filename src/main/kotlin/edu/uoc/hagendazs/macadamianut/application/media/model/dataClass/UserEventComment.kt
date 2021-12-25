package edu.uoc.hagendazs.macadamianut.application.media.model.dataClass

import java.time.LocalDateTime

data class UserEventComment(
    val userId: String,
    val eventId: String,
    val createdAt: LocalDateTime,
    val comment: String,
)
