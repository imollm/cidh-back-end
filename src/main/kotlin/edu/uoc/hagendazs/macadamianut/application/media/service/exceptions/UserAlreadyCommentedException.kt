package edu.uoc.hagendazs.macadamianut.application.media.service.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class UserAlreadyCommentedException(
    message: String
) : RuntimeException(message)