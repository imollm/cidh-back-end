package edu.uoc.hagendazs.macadamianut.application.event.categories.service.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class CategoryAlreadyExistsException(
    message: String
): RuntimeException(message)