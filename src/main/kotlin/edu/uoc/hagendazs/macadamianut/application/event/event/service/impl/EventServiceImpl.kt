package edu.uoc.hagendazs.macadamianut.application.event.event.service.impl

import edu.uoc.hagendazs.macadamianut.application.event.category.model.repo.CategoryRepo
import edu.uoc.hagendazs.macadamianut.application.event.event.entrypoint.input.NewOrUpdateEventRequest
import edu.uoc.hagendazs.macadamianut.application.event.event.entrypoint.output.EventResponse
import edu.uoc.hagendazs.macadamianut.application.event.event.model.repo.EventRepo
import edu.uoc.hagendazs.macadamianut.application.event.event.service.EventService
import edu.uoc.hagendazs.macadamianut.application.event.event.service.exceptions.EventAlreadyExistsException
import edu.uoc.hagendazs.macadamianut.application.event.event.service.exceptions.EventDoesNotExistException
import edu.uoc.hagendazs.macadamianut.application.event.event.service.exceptions.UnableToCreateOrUpdateEventFromGivenData
import edu.uoc.hagendazs.macadamianut.application.event.label.model.repo.LabelRepo
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EventServiceImpl : EventService {

    @Autowired
    private lateinit var eventRepo: EventRepo

    @Autowired
    private lateinit var categoryRepo: CategoryRepo

    @Autowired
    private lateinit var labelRepo: LabelRepo

    private val logger = KotlinLogging.logger {}

    override fun createEvent(newEvent: NewOrUpdateEventRequest): EventResponse? {
        // check duplicate event names
        eventRepo.findByName(newEvent.name, null)?.let {
            throw EventAlreadyExistsException("${it.name} already exists in ")
        }
        val labelsFromDb = labelRepo.findLabelsWithId(newEvent.labelIds).map { it.id }

        // check that the label ids do exist.
        if (labelsFromDb.size != newEvent.labelIds.size) {
            throw UnableToCreateOrUpdateEventFromGivenData(
                "The following label ids do not exist: ${newEvent.labelIds.minus(labelsFromDb.toSet())}"
            )
        }
        // check that category exists
        val category = categoryRepo.findByName(newEvent.category) ?: run {
            throw UnableToCreateOrUpdateEventFromGivenData(
                "Unable to create Event. Category with name $newEvent.category " +
                        "does not exist"
            )
        }

        val dbEvent = newEvent.toInternalEventModel(categoryId = category.id)
        val createdEvent = eventRepo.create(dbEvent, newEvent.labelIds)
        logger.info { "Event created with name ${newEvent.name} and id $newEvent.id" }
        return createdEvent
    }

    override fun updateEvent(
        eventId: String,
        updateEventRequest: NewOrUpdateEventRequest,
        requesterUserId: String?
    ): EventResponse? {
        eventRepo.findById(eventId, requesterUserId) ?: run {
            EventDoesNotExistException("Event with id $eventId does not exist in this server")
        }
        val category = categoryRepo.findByName(updateEventRequest.category) ?: run {
            throw UnableToCreateOrUpdateEventFromGivenData(
                "Category with name ${updateEventRequest.category} does not exist"
            )
        }
        return eventRepo.update(eventId, updateEventRequest, requesterUserId, category.id)
    }

    override fun findById(eventId: String, requesterUserId: String?): EventResponse? {
        return eventRepo.findById(eventId, requesterUserId)
    }

    override fun findEventsWithFilters(
        labels: Collection<String>,
        categories: Collection<String>,
        names: Collection<String>,
        admins: Collection<String>,
        limit: Int?,
        requesterUserId: String?
    ): Collection<EventResponse> {
        return eventRepo.eventsWithFilters(
            labels,
            categories,
            names,
            admins,
            limit,
            requesterUserId
        )
    }
}
