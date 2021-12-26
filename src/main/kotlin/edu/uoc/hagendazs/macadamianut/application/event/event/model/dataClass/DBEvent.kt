package edu.uoc.hagendazs.macadamianut.application.event.event.model.dataClass

import edu.uoc.hagendazs.macadamianut.application.event.event.model.CIDHEvent
import java.net.URI
import java.time.LocalDateTime
import java.util.*

data class DBEvent (
    override val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val headerImage: URI?,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val categoryId: String? = null,
    val organizerId: String,
    val eventUrl: String,
): CIDHEvent
