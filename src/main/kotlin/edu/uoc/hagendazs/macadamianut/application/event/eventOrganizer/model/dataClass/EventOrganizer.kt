package edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.model.dataClass

import java.time.LocalDateTime
import java.util.*

data class EventOrganizer(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val admin: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
