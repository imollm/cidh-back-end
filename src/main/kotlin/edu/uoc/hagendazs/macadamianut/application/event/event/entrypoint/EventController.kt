package edu.uoc.hagendazs.macadamianut.application.event.event.entrypoint

import edu.uoc.hagendazs.macadamianut.application.event.event.entrypoint.input.NewOrUpdateEventRequest
import edu.uoc.hagendazs.macadamianut.application.event.event.model.dataclasses.CIDHEvent
import edu.uoc.hagendazs.macadamianut.application.event.event.service.EventService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.server.ResponseStatusException
import java.net.URI
import javax.servlet.http.HttpServletRequest

@Controller
class EventController {

    @Autowired
    private lateinit var eventService: EventService

    @PostMapping(value = ["/events"])
    fun createEvent(
        @RequestBody newUserReq: NewOrUpdateEventRequest,
        request: HttpServletRequest,
    ): ResponseEntity<CIDHEvent> {
        val newEvent = newUserReq.toInternalEventModel()
        val createdEvent = eventService.createEvent(newEvent) ?: run {
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
    ) {

    }

    /*
    // get single event

    // get events by:
        //  label
        //  category
        //  name

    // create event
    // update event



     */
}
