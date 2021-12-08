package edu.uoc.hagendazs.macadamianut.application.event.category.service.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class UnableToCreateCategory(
    message: String
) : RuntimeException(message)
