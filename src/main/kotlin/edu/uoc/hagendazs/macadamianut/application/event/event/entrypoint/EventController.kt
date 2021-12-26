package edu.uoc.hagendazs.macadamianut.application.event.event.entrypoint

import edu.uoc.hagendazs.macadamianut.application.event.event.entrypoint.input.NewOrUpdateEventRequest
import edu.uoc.hagendazs.macadamianut.application.event.event.model.dataClass.CIDHEvent
import edu.uoc.hagendazs.macadamianut.application.event.event.service.EventService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.net.URI
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("/api/v1")
class EventController {

    @Autowired
    private lateinit var eventService: EventService

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = ["/events"])
    fun createEvent(
        @RequestBody newUserReq: NewOrUpdateEventRequest,
        request: HttpServletRequest,
    ): ResponseEntity<CIDHEvent> {
        val newEvent = newUserReq.toInternalEventModel()
        val createdEvent = eventService.createEvent(
            newEvent = newEvent,
            categoryName = newUserReq.category
        ) ?: run {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to create event!")
        }
        val eventUriString = request.requestURL.toString().plus("/${createdEvent.id}")
        val eventUri = URI.create(eventUriString)
        return ResponseEntity.created(eventUri).body(createdEvent)
    }

    @PostMapping(value = ["/events/{eventId}"])
    fun updateEvent(
        @RequestBody newUserReq: NewOrUpdateEventRequest,
        @PathVariable("eventId") eventId: String,
    ): ResponseEntity<CIDHEvent> {
        val eventToUpdate = newUserReq.toInternalEventModel().copy(id = eventId)
        val updatedEvent = eventService.updateEvent(eventToUpdate) ?: run {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to update event!")
        }
        return ResponseEntity.ok(updatedEvent)
    }

    @GetMapping(value = ["/events/{eventId}"])
    fun getEventById(
        @PathVariable("eventId") eventId: String,
    ): ResponseEntity<CIDHEvent> {
        val event = eventService.findById(eventId) ?: run {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id $eventId not found in this server")
        }
        return ResponseEntity.ok(event)
    }

    @GetMapping(value = ["/events"])
    fun getEventsByFilter(
        @RequestParam(required = false) label: Collection<String>?,
        @RequestParam(required = false) category: Collection<String>?,
        @RequestParam(required = false) name: Collection<String>?,
        @RequestParam(required = false) admin: Collection<String>?,
        @RequestParam(required = false) limit: Int?,
    ): ResponseEntity<Collection<CIDHEvent>> {
        val eventCollection = eventService.findEventsWithFilters(
            labels = label ?: emptyList(),
            categories = category  ?: emptyList(),
            names = name  ?: emptyList(),
            admins = admin ?: emptyList(),
            limit = limit,
        )

        return ResponseEntity.ok(eventCollection)
    }
}
