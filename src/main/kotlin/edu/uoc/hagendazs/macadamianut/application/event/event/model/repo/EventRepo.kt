package edu.uoc.hagendazs.macadamianut.application.event.event.model.repo

import edu.uoc.hagendazs.macadamianut.application.event.event.entrypoint.output.EventResponse
import edu.uoc.hagendazs.macadamianut.application.event.event.model.dataClass.DBEvent


interface EventRepo {
    fun findByName(name: String): EventResponse?
    fun create(newEvent: DBEvent, labelIds: Collection<String>): EventResponse?
    fun findById(id: String): EventResponse?
    fun update(eventToUpdate: DBEvent, labelIds: Collection<String>): EventResponse?
    fun eventsWithFilters(
        labels: Collection<String>,
        categories: Collection<String>,
        names: Collection<String>,
        admins: Collection<String>,
        limit: Int?
    ): Collection<EventResponse>

    fun findEventsWithLabels(labels: Collection<String>): Collection<EventResponse>
    fun findAllEvents(): Collection<EventResponse>
}
