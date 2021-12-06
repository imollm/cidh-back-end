package edu.uoc.hagendazs.macadamianut.application.user.entrypoint.output

data class AuthenticationResponse(
    val jwt: String,
    val refreshToken: String,
    val tokenType: String = "Bearer",
    val permissions: String,
)