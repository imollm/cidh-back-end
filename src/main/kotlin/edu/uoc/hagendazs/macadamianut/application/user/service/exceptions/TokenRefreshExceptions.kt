package edu.uoc.hagendazs.macadamianut.application.user.service.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.FORBIDDEN)
class TokenRefreshException(
    token: String,
    message: String,
) : RuntimeException("Failed for $token: $message")