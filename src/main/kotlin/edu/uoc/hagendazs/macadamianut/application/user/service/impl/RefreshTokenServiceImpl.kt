package edu.uoc.hagendazs.macadamianut.application.user.service.impl

import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.MNUser
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.RefreshToken
import edu.uoc.hagendazs.macadamianut.application.user.service.RefreshTokenService
import org.springframework.stereotype.Service

@Service
class RefreshTokenServiceImpl: RefreshTokenService {
    override fun findByToken(token: String?): RefreshToken? {
        TODO("Not yet implemented")
    }

    override fun createRefreshToken(personId: String?): RefreshToken? {
        TODO("Not yet implemented")
    }

    override fun isTokenExpired(token: RefreshToken?): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteTokensForPerson(personId: String?) {
        TODO("Not yet implemented")
    }

    override fun isTokenNotExpiredAndValid(refreshToken: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteToken(refreshToken: String) {
        TODO("Not yet implemented")
    }

    override fun refreshTokenOwner(refreshToken: String): MNUser? {
        TODO("Not yet implemented")
    }

    override fun invalidateRefreshTokenIfAuthorised(
        refreshToken: String,
        requester: String,
        isAdmin: Boolean
    ): Boolean {
        TODO("Not yet implemented")
    }
}