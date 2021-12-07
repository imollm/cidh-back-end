package edu.uoc.hagendazs.macadamianut.application.event.categories.model.dataClass

import java.time.LocalDateTime
import java.util.UUID

data class Category(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
