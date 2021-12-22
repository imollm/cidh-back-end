package edu.uoc.hagendazs.macadamianut.application.event.event.model.repo

import edu.uoc.hagendazs.macadamianut.application.event.event.model.dataclasses.CIDHEvent

interface EventRepo {
    fun findByName(name: String): CIDHEvent?
    fun create(newEvent: CIDHEvent): CIDHEvent?
    fun findById(id: String): CIDHEvent?
    fun update(eventToUpdate: CIDHEvent): CIDHEvent?
    fun eventsWithFilters(
        labels: Collection<String>,
        categories: Collection<String>,
        names: Collection<String>
    ): Collection<CIDHEvent>

    fun findEventsWithLabels(labels: Collection<String>): Collection<CIDHEvent>
    fun findAllEvents(): Collection<CIDHEvent>
}