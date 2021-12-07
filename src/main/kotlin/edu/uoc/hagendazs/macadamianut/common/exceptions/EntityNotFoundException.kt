package edu.uoc.hagendazs.macadamianut.common.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class EntityNotFoundException(
    message: String
) : RuntimeException(message)

