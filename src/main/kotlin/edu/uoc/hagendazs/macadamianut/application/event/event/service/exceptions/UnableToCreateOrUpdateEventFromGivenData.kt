package edu.uoc.hagendazs.macadamianut.application.event.event.service.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class UnableToCreateOrUpdateEventFromGivenData(
    reason: String
) : RuntimeException(reason)