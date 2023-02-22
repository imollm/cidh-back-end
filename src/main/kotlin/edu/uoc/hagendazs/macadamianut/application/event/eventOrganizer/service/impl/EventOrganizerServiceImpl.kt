package edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.service.impl

import edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.entrypoint.input.UpdateEventOrganizerRequest
import edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.model.dataClass.EventOrganizer
import edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.model.repo.EventOrganizerRepo
import edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.service.EventOrganizerService
import edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.service.exceptions.EventOrganizerAlreadyExists
import edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.service.exceptions.EventOrganizerNotFound
import edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.service.exceptions.UnableToCreateEventOrganizer
import edu.uoc.hagendazs.macadamianut.common.HTTPMessages
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EventOrganizerServiceImpl: EventOrganizerService {

    @Autowired
    lateinit var eventOrganizerRepo: EventOrganizerRepo

    override fun addEventOrganizer(eventOrganizer: EventOrganizer): EventOrganizer? {
        if (eventOrganizerRepo.existsByName(eventOrganizer.name)) {
            throw EventOrganizerAlreadyExists(HTTPMessages.ALREADY_EXISTS)
        }
        return eventOrganizerRepo.addEventOrganizer(eventOrganizer) ?: run {
            throw UnableToCreateEventOrganizer(HTTPMessages.UNABLE_TO_CREATE)
        }
    }

    override fun updateEventOrganizer(eventOrganizerId: String, updateEventOrganizerRequest: UpdateEventOrganizerRequest): EventOrganizer? {
        val eventOrganizerBody = updateEventOrganizerRequest.receive()
        val eventOrganizerToUpdate = eventOrganizerRepo.findById(eventOrganizerId) ?: run {
            throw EventOrganizerNotFound(HTTPMessages.NOT_FOUND)
        }
        return eventOrganizerRepo.updateEventOrganizer(this.copyEventOrganizerEntity(eventOrganizerToUpdate, eventOrganizerBody))
    }

    override fun getEventOrganizer(eventOrganizerId: String): EventOrganizer? {
        return eventOrganizerRepo.getEventOrganizer(eventOrganizerId)
    }

    override fun listAllEventOrganizers(): Collection<EventOrganizer> {
        return eventOrganizerRepo.listAllEventOrganizers()
    }

    private fun copyEventOrganizerEntity(eventOrganizerToUpdate: EventOrganizer, incomingEventOrganizer: EventOrganizer): EventOrganizer {
        return eventOrganizerToUpdate.copy(
            id = eventOrganizerToUpdate.id,
            name = incomingEventOrganizer.name,
            description = incomingEventOrganizer.description,
            admin = incomingEventOrganizer.admin
        )
    }
}