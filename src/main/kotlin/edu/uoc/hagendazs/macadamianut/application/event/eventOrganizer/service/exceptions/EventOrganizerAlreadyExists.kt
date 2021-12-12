package edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.service.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class EventOrganizerAlreadyExists (
    message: String
): RuntimeException(message)
