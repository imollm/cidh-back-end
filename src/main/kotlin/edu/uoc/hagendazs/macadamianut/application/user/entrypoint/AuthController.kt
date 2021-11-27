package edu.uoc.hagendazs.macadamianut.application.user.entrypoint

import edu.uoc.hagendazs.macadamianut.application.user.entrypoint.input.RefreshTokenInvalidateRequest
import edu.uoc.hagendazs.macadamianut.application.user.entrypoint.input.RefreshTokenReq
import edu.uoc.hagendazs.macadamianut.application.user.entrypoint.input.UserPasswordReq
import edu.uoc.hagendazs.macadamianut.application.user.entrypoint.output.AuthenticationResponse
import edu.uoc.hagendazs.macadamianut.application.user.service.AppUserDetailService
import edu.uoc.hagendazs.macadamianut.application.user.service.RefreshTokenService
import edu.uoc.hagendazs.macadamianut.application.user.service.helper.JwtHelper
import edu.uoc.hagendazs.macadamianut.common.entrypoint.output.MessageResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.server.ResponseStatusException

@Controller
class AuthController {

    @Autowired
    private lateinit var jwtHelper: JwtHelper

    @Autowired
    private lateinit var userDetailsService: AppUserDetailService

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var refreshTokenService: RefreshTokenService

    private val deleteRefreshTokenGenericError = ResponseStatusException(
        HttpStatus.FORBIDDEN,
        "You are not authorized to delete the token, or the token does not exist"
    )

    @PostMapping(path = ["/users/login"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun login(
        @RequestBody loginReq: UserPasswordReq,
    ): ResponseEntity<AuthenticationResponse> {
        val email = loginReq.email
        val password = loginReq.password

        val userDetails = userDetailsService.loadUserByUsername(email)

        if (!passwordEncoder.matches(password, userDetails.password)) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect User / Password combination")
        }
        return generateAuthenticationResponseForUser(userDetails)
    }

    @PostAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN', 'USER')")
    @PostMapping(path = ["/v2/users/invalidate-token"])
    fun invalidateRefreshToken(
        @RequestBody refreshTokenInvalidateReq: RefreshTokenInvalidateRequest,
        jwtToken: Authentication,
    ): ResponseEntity<MessageResponse> {

        val isAdmin = jwtToken.authorities.any { it.authority == "ADMIN" }
        val requesterPersonId = jwtToken.name

        val isTokenDeleted = refreshTokenService.invalidateRefreshTokenIfAuthorised(
            refreshToken = refreshTokenInvalidateReq.refreshToken,
            requester = requesterPersonId,
            isAdmin = isAdmin
        )
        if (!isTokenDeleted) {
            throw deleteRefreshTokenGenericError
        }
        return ResponseEntity.ok(MessageResponse("Token deleted correctly"))

    }

    @PostMapping(path = ["/v2/users/refresh-token"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun renewJwtFromRefreshToken(
        @RequestBody refreshTokenReq: RefreshTokenReq
    ): ResponseEntity<AuthenticationResponse> {
        val canAuthenticateAgain = refreshTokenService.isTokenNotExpiredAndValid(refreshTokenReq.refreshToken)
        if (!canAuthenticateAgain) {
            throw ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "The refresh token is not valid (expired or nonexistent)"
            )
        }
        val refreshTokenObj = refreshTokenService.findByToken(refreshTokenReq.refreshToken)
        val userDetails = userDetailsService.loadUserById(refreshTokenObj?.personId)

        val authResponse = generateAuthenticationResponseForUser(userDetails)
        refreshTokenService.deleteToken(refreshTokenReq.refreshToken)
        return authResponse
    }

    private fun generateAuthenticationResponseForUser(
        userDetails: UserDetails
    ): ResponseEntity<AuthenticationResponse> {
        val claims = generateClaims(userDetails)
        val jwt = jwtHelper.createJwtForClaims(userDetails.username, claims)
        val refreshToken = refreshTokenService.createRefreshToken(userDetails.username) ?: run {
            throw IllegalStateException("Unable to save refresh token in DB!")
        }
        return ResponseEntity.ok(AuthenticationResponse(jwt, refreshToken.id))
    }


    private fun generateClaims(userDetails: UserDetails): Map<String, String> {
        val authorities = userDetails.authorities.joinToString { it.authority }

        return mapOf(
            JwtConstants.USERNAME to userDetails.username,
            JwtConstants.AUTHORITIES to authorities,
        )
    }


}