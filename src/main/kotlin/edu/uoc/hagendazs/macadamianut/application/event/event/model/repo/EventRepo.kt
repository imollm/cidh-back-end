package edu.uoc.hagendazs.macadamianut.application.event.event.model.repo

import edu.uoc.hagendazs.macadamianut.application.event.event.model.dataClass.CIDHEvent

interface EventRepo {
    fun findByName(name: String): CIDHEvent?
    fun create(newEvent: CIDHEvent): CIDHEvent?
    fun findById(id: String): CIDHEvent?
    fun update(eventToUpdate: CIDHEvent): CIDHEvent?
    fun eventsWithFilters(
        labels: Collection<String>,
        categories: Collection<String>,
        names: Collection<String>,
        admins: Collection<String>,
        limit: Int?
    ): Collection<CIDHEvent>

    fun findEventsWithLabels(labels: Collection<String>): Collection<CIDHEvent>
    fun findAllEvents(): Collection<CIDHEvent>
}
