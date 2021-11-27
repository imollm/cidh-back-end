package edu.uoc.hagendazs.macadamianut.application.user.model.dataClass

import java.util.*

data class UserRole(
    val id: String = UUID.randomUUID().toString(),
    val person: String? = null,
    val role: String? = null,
)