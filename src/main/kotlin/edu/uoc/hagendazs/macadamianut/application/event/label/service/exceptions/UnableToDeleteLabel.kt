package edu.uoc.hagendazs.macadamianut.application.event.label.service.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class UnableToDeleteLabel(
    message: String
) : RuntimeException(message)
