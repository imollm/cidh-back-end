package edu.uoc.hagendazs.macadamianut.application.event.event.service

import edu.uoc.hagendazs.macadamianut.application.event.event.model.dataclasses.CIDHEvent

interface EventService {
    fun createEvent(newEvent: CIDHEvent): CIDHEvent?
    fun updateEvent(eventToUpdate: CIDHEvent): CIDHEvent?
    fun findById(eventId: String): CIDHEvent?
    fun findEventsWithFilters(
        labels: Collection<String>,
        categories: Collection<String>,
        names: Collection<String>
    ): Collection<CIDHEvent>

}
