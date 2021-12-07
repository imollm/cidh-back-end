package edu.uoc.hagendazs.macadamianut.application.user.entrypoint.output

import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.RoleEnum

data class AuthenticationResponse(
    val jwt: String,
    val refreshToken: String,
    val tokenType: String = "Bearer",
    val role: RoleEnum,
    val permissions: String,
)