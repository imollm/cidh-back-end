package edu.uoc.hagendazs.macadamianut.application.event.event.entrypoint.input

import edu.uoc.hagendazs.macadamianut.application.event.event.model.dataclasses.CIDHEvent
import java.net.URI
import java.time.LocalDateTime

data class NewOrUpdateEventRequest(
    val name: String,
    val description: String,
    val headerImage: URI?,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
) {
    fun toInternalEventModel(): CIDHEvent {
        return CIDHEvent(
            name = name,
            description = description,
            headerImage = headerImage,
            startDate = startDate,
            endDate = endDate,
        )
    }
}
