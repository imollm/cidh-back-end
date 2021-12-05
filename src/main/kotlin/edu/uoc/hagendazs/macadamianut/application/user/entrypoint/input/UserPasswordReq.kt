package edu.uoc.hagendazs.macadamianut.application.user.entrypoint.input

import javax.validation.constraints.NotBlank

data class UserPasswordReq (
    @field:NotBlank
    var email: String,
    @field:NotBlank
    val password: String,
)
