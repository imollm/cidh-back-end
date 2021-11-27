package edu.uoc.hagendazs.macadamianut.application.user.model.dataClass

import com.fasterxml.jackson.annotation.JsonIgnore
import org.intellij.lang.annotations.Language
import java.time.LocalDateTime
import java.util.*

data class MNUser(
    val id: String = UUID.randomUUID().toString(),
    val email: String,
    @JsonIgnore
    val password: String,
    val name: String,
    val lastName: String,
    val createdAt: LocalDateTime,
    val preferredLanguage: SystemLanguage
    )