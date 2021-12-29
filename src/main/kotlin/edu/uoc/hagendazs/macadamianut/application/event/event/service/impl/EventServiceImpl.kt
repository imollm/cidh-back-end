package edu.uoc.hagendazs.macadamianut.application.event.event.service.impl

import edu.uoc.hagendazs.macadamianut.application.event.category.model.repo.CategoryRepo
import edu.uoc.hagendazs.macadamianut.application.event.event.entrypoint.output.EventResponse
import edu.uoc.hagendazs.macadamianut.application.event.event.model.dataClass.DBEvent
import edu.uoc.hagendazs.macadamianut.application.event.event.model.repo.EventRepo
import edu.uoc.hagendazs.macadamianut.application.event.event.service.EventService
import edu.uoc.hagendazs.macadamianut.application.event.event.service.exceptions.EventAlreadyExistsException
import edu.uoc.hagendazs.macadamianut.application.event.event.service.exceptions.EventDoesNotExistException
import edu.uoc.hagendazs.macadamianut.application.event.event.service.exceptions.UnableToCreateEventFromGivenData
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

    override fun createEvent(newEvent: DBEvent, categoryName: String?, labelIds: Collection<String>): EventResponse? {
        // check duplicate event names
        eventRepo.findByName(newEvent.name, null)?.let {
            throw EventAlreadyExistsException("${it.name} already exists in ")
        }
        val labelsFromDb = labelRepo.findLabelsWithId(labelIds).map { it.id }

        if (labelsFromDb.size != labelIds.size) {
            throw UnableToCreateEventFromGivenData(
                "The following label ids do not exist: ${labelIds.minus(labelsFromDb.toSet())}"
            )
        }
        // check that category exists
        val category = categoryRepo.findByName(categoryName) ?: run {
            throw UnableToCreateEventFromGivenData(
                "Unable to create Event. Category with name $categoryName " +
                        "does not exist"
            )
        }

        val createdEvent = eventRepo.create(newEvent.copy(categoryId = category.id), labelIds)
        logger.info { "Event created with name ${newEvent.name} and id $newEvent.id" }
        return createdEvent
    }

    override fun updateEvent(
        eventToUpdate: DBEvent,
        labelIds: Collection<String>,
        requesterUserId: String?
    ): EventResponse? {
        eventRepo.findById(eventToUpdate.id, requesterUserId) ?: run {
            EventDoesNotExistException("Event with id ${eventToUpdate.id} does not exist in this server")
        }
        return eventRepo.update(eventToUpdate, labelIds, requesterUserId)
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
