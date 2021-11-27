package edu.uoc.hagendazs.macadamianut.application.user.service.helper

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.time.Instant
import java.util.*

@Component
class JwtHelper(private val privateKey: RSAPrivateKey, private val publicKey: RSAPublicKey) {

    @Value("\${security.jwt.jwtTokenExpirationMinutes}")
    private lateinit var jwtTokenExpirationMinutes: Number

    fun createJwtForClaims(subject: String?, claims: Map<String, String>): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = Instant.now().toEpochMilli()
        calendar.add(Calendar.MINUTE, jwtTokenExpirationMinutes.toInt())
        val jwtBuilder: JWTCreator.Builder = JWT.create().withSubject(subject)

        // Add claims
        claims.forEach { (claimName, claimValue) ->
            jwtBuilder.withClaim(
                claimName,
                claimValue
            )
        }

        // Add expiredAt and etc
        return jwtBuilder
            .withNotBefore(Date())
            .withExpiresAt(calendar.time)
            .sign(Algorithm.RSA256(publicKey, privateKey))
    }
}