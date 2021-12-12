package edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.entrypoint

import edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.entrypoint.input.CreateEventOrganizerRequest
import edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.entrypoint.input.UpdateEventOrganizerRequest
import edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.model.dataClass.EventOrganizer
import edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.service.EventOrganizerService
import edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.service.exceptions.EventOrganizerNotFound
import edu.uoc.hagendazs.macadamianut.common.HTTPMessages
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.net.URI
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/v1/event-organizer")
class EventOrganizerController {

    @Autowired
    lateinit var eventOrganizerService: EventOrganizerService

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PostMapping(value = [""])
    fun createEventOrganizer(
        @RequestBody newEventOrganizerRequest: CreateEventOrganizerRequest,
        request: HttpServletRequest
    ): ResponseEntity<EventOrganizer> {

        val incomingEventOrganizer = newEventOrganizerRequest.receive()
        val eventOrganizer = eventOrganizerService.addEventOrganizer(incomingEventOrganizer)
        eventOrganizer ?: run {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                HTTPMessages.ERROR_SAVING_A_RESOURCE
            )
        }
        val eventOrganizerUriString = request.requestURL.toString().plus("/${eventOrganizer.id}")
        val eventOrganizerUri = URI.create(eventOrganizerUriString)
        return ResponseEntity.created(eventOrganizerUri).body(eventOrganizer)
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PutMapping(value = ["/{eventOrganizerId}"])
    fun updateEventOrganizer(
        @PathVariable eventOrganizerId: String,
        @RequestBody updateEventOrganizerRequest: UpdateEventOrganizerRequest
    ): ResponseEntity<EventOrganizer> {
        val eventOrganizer = eventOrganizerService.updateEventOrganizer(eventOrganizerId, updateEventOrganizerRequest)
        return ResponseEntity.ok(eventOrganizer)
    }

    @GetMapping(value = ["/{eventOrganizerId}"])
    fun getEventOrganizerById(
        @PathVariable eventOrganizerId: String
    ): ResponseEntity<EventOrganizer> {
        val eventOrganizer = eventOrganizerService.showEventOrganizer(eventOrganizerId) ?: run {
            throw EventOrganizerNotFound(HTTPMessages.NOT_FOUND)
        }
        return ResponseEntity.ok(eventOrganizer)
    }

    @GetMapping(value = [""])
    fun getAllEventOrganizers(): ResponseEntity<Collection<EventOrganizer>> {
        val eventOrganizers = eventOrganizerService.listAllEventOrganizers()
        return ResponseEntity.ok(eventOrganizers)
    }

}