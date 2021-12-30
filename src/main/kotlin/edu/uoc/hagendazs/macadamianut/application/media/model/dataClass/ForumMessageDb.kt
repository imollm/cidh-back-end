package edu.uoc.hagendazs.macadamianut.application.media.model.dataClass

import java.time.LocalDateTime

data class ForumMessageDb(
    val id: String,
    val authorUserId: String,
    val eventId: String,
    val parentId: String?,
    val createdAt: LocalDateTime,
    val message: String,
)
