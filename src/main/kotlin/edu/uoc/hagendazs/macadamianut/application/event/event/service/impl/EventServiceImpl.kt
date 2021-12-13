package edu.uoc.hagendazs.macadamianut.application.event.event.service.impl

import edu.uoc.hagendazs.macadamianut.application.event.category.model.repo.CategoryRepo
import edu.uoc.hagendazs.macadamianut.application.event.category.service.exceptions.CategoryNotFoundException
import edu.uoc.hagendazs.macadamianut.application.event.event.model.dataclasses.CIDHEvent
import edu.uoc.hagendazs.macadamianut.application.event.event.model.repo.EventRepo
import edu.uoc.hagendazs.macadamianut.application.event.event.service.EventService
import edu.uoc.hagendazs.macadamianut.application.event.event.service.exceptions.EventAlreadyExistsException
import edu.uoc.hagendazs.macadamianut.application.event.event.service.exceptions.EventDoesNotExistException
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EventServiceImpl: EventService {

    @Autowired
    private lateinit var eventRepo: EventRepo

    @Autowired
    private lateinit var categoryRepo: CategoryRepo

    private val logger = KotlinLogging.logger {}

    override fun createEvent(newEvent: CIDHEvent): CIDHEvent? {
        // check duplicate event names
        eventRepo.findByName(newEvent.name)?.let {
            throw EventAlreadyExistsException("${it.name} already exists in ")
        }
        // check that category exists
        categoryRepo.findByName(newEvent.category) ?: run {
            throw CategoryNotFoundException("Unable to update Event. Category with name ${newEvent.category} " +
                    "does not exist")
        }

        val createdEvent =  eventRepo.create(newEvent)
        logger.info { "Event created with name ${newEvent.name} and id $newEvent.id" }
        return createdEvent
    }

    override fun updateEvent(eventToUpdate: CIDHEvent): CIDHEvent? {
        eventRepo.findById(eventToUpdate.id) ?: run {
            EventDoesNotExistException("Event with id ${eventToUpdate.id} does not exist in this server")
        }
        return eventRepo.update(eventToUpdate)
    }

    override fun findById(eventId: String): CIDHEvent? {
        return eventRepo.findById(eventId)
    }

    override fun findEventsWithFilters(
        labels: Collection<String>,
        categories: Collection<String>,
        names: Collection<String>
    ): Collection<CIDHEvent> {
        return eventRepo.eventsWithFilters(labels, categories, names)
    }
}