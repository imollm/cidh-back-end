package edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.service.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class EventOrganizerMissingValues (
    message: String
): RuntimeException(message)