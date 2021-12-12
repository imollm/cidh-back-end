package edu.uoc.hagendazs.macadamianut.application.event.event.model.dataclasses

import java.net.URI
import java.time.LocalDateTime
import java.util.*

data class CIDHEvent (
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val headerImage: URI?,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
)
