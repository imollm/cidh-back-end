package edu.uoc.hagendazs.macadamianut.application.event.event.entrypoint.input

import edu.uoc.hagendazs.macadamianut.application.event.event.model.dataClass.CIDHEvent
import java.net.URI
import java.time.LocalDateTime

data class NewOrUpdateEventRequest(
    val name: String,
    val description: String,
    val headerImage: URI?,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val category: String?,
    val organizerId: String,
) {

    init {
        require(endDate.isAfter(startDate)) { "Event starting date should be before end date: \n " +
                "\t start date: $startDate" +
                "\n\t end date: $endDate" }
        require(name.isNotBlank()) { "Event name cannot be empty" }
    }

    fun toInternalEventModel(): CIDHEvent {
        return CIDHEvent(
            name = name,
            description = description,
            headerImage = headerImage,
            startDate = startDate,
            endDate = endDate,
            organizerId = organizerId
        )
    }
}
