package edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.service

import edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.entrypoint.input.UpdateEventOrganizerRequest
import edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.model.dataClass.EventOrganizer

interface EventOrganizerService {
    fun addEventOrganizer(eventOrganizer: EventOrganizer): EventOrganizer?
    fun updateEventOrganizer(eventOrganizerId: String, updateEventOrganizerRequest: UpdateEventOrganizerRequest): EventOrganizer?
    fun showEventOrganizer(eventOrganizerId: String): EventOrganizer?
    fun listAllEventOrganizers(): Collection<EventOrganizer>
}