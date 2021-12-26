package edu.uoc.hagendazs.macadamianut.application.event.event.service

import edu.uoc.hagendazs.macadamianut.application.event.event.entrypoint.output.EventResponse
import edu.uoc.hagendazs.macadamianut.application.event.event.model.dataClass.DBEvent

interface EventService {
    fun createEvent(newEvent: DBEvent, categoryName: String?): EventResponse?
    fun updateEvent(eventToUpdate: DBEvent): EventResponse?
    fun findById(eventId: String): EventResponse?
    fun findEventsWithFilters(
        labels: Collection<String>,
        categories: Collection<String>,
        names: Collection<String>,
        admins: Collection<String>,
        limit: Int?,
    ): Collection<EventResponse>
}
