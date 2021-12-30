package edu.uoc.hagendazs.macadamianut.application.event.event.service

import edu.uoc.hagendazs.macadamianut.application.event.event.entrypoint.input.NewOrUpdateEventRequest
import edu.uoc.hagendazs.macadamianut.application.event.event.entrypoint.output.EventResponse

interface EventService {
    fun createEvent(
        newEvent:NewOrUpdateEventRequest
    ): EventResponse?

    fun updateEvent(
        eventId: String,
        updateEventRequest: NewOrUpdateEventRequest,
        requesterUserId: String?,
    ): EventResponse?

    fun findById(
        eventId: String,
        requesterUserId: String?,
    ): EventResponse?

    fun findEventsWithFilters(
        labels: Collection<String>,
        categories: Collection<String>,
        names: Collection<String>,
        admins: Collection<String>,
        limit: Int?,
        requesterUserId: String?,
    ): Collection<EventResponse>
}
