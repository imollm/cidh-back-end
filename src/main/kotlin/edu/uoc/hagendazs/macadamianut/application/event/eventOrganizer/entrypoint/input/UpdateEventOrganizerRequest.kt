package edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.entrypoint.input

import edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.model.dataClass.EventOrganizer
import edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.service.exceptions.EventOrganizerMissingValues
import edu.uoc.hagendazs.macadamianut.common.HTTPMessages

data class UpdateEventOrganizerRequest(
    val name: String,
    val description: String,
    val admin: String
) {

    fun receive(): EventOrganizer {
        if (name.isBlank() || description.isBlank() || admin.isBlank()) {
            throw EventOrganizerMissingValues(HTTPMessages.MISSING_VALUES)
        }
        return EventOrganizer(
            name = name,
            description = description,
            admin = admin
        )
    }
}
