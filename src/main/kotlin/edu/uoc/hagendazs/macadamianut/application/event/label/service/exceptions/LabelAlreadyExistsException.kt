package edu.uoc.hagendazs.macadamianut.application.event.label.service.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class LabelAlreadyExistsException(
    message: String
): RuntimeException(message)
