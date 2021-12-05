package edu.uoc.hagendazs.macadamianut.application.user.model.dataClass

import java.time.LocalDateTime
import java.util.*

data class RefreshToken(
    val id: String = UUID.randomUUID().toString(),
    val personId: String,
    val expiryDate: LocalDateTime,
)