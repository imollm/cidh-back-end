package edu.uoc.hagendazs.macadamianut.application.user.service

import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.MNUser
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.RefreshToken

interface RefreshTokenService {
    fun findByToken(token: String?): RefreshToken?
    fun createRefreshToken(personId: String?): RefreshToken?
    fun isTokenExpired(token: RefreshToken?): Boolean
    fun deleteTokensForUserId(userId: String?)
    fun isTokenNotExpiredAndValid(refreshToken: String): Boolean
    fun deleteToken(refreshToken: String)
    fun refreshTokenOwner(refreshToken: String): MNUser?
    fun invalidateRefreshTokenIfAuthorised(refreshToken: String, requester: String, isAdmin: Boolean): Boolean
}