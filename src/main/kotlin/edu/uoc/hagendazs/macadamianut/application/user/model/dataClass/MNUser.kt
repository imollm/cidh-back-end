package edu.uoc.hagendazs.macadamianut.application.user.model.dataClass

import com.fasterxml.jackson.annotation.JsonIgnore
import org.jooq.Field
import java.time.LocalDateTime
import java.util.*

data class MNUser(
    val id: String = UUID.randomUUID().toString(),
    val email: String,
    @JsonIgnore
    val password: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val nif: String? = null,
    val address: String? = null,
    val isValidEmail: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val preferredLanguage: SystemLanguage = SystemLanguage.spanish,
)