package edu.uoc.hagendazs.macadamianut.application.event.categories.service.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class CategoryMissingValues (
    message: String
): RuntimeException(message)