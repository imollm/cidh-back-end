package edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.model.repo

import edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.model.dataClass.EventOrganizer

interface EventOrganizerRepo {
    fun addEventOrganizer(eventOrganizer: EventOrganizer): EventOrganizer?
    fun updateEventOrganizer(eventOrganizer: EventOrganizer): EventOrganizer?
    fun getEventOrganizer(eventOrganizerId: String): EventOrganizer?
    fun listAllEventOrganizers(): Collection<EventOrganizer>
    fun existsByName(eventOrganizerName: String): Boolean
    fun findByName(eventOrganizerName: String): EventOrganizer?
    fun findById(eventOrganizerId: String): EventOrganizer?
}