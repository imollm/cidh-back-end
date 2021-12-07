package edu.uoc.hagendazs.macadamianut.application.user.model.repo

import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.RefreshToken

interface RefreshTokenRepo {
    fun findByToken(token: String): RefreshToken?
    fun findTokensForUserId(userId: String): Collection<RefreshToken>
    fun save(refreshToken: RefreshToken): RefreshToken?
    fun deleteToken(refreshToken: String): Int
    fun deleteTokensForUser(userId: String): Int
}