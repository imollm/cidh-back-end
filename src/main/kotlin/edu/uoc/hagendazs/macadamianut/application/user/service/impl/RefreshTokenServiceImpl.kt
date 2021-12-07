package edu.uoc.hagendazs.macadamianut.application.user.service.impl

import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.MNUser
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.RefreshToken
import edu.uoc.hagendazs.macadamianut.application.user.model.repo.RefreshTokenRepo
import edu.uoc.hagendazs.macadamianut.application.user.service.RefreshTokenService
import edu.uoc.hagendazs.macadamianut.application.user.service.UserService
import edu.uoc.hagendazs.macadamianut.application.user.service.exceptions.TokenRefreshException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class RefreshTokenServiceImpl: RefreshTokenService {

    @Value("\${security.jwt.refreshTokenExpirationMinutes}")
    private lateinit var refreshTokenExpirationHours: Number

    @Autowired
    private lateinit var refreshTokenRepo: RefreshTokenRepo

    @Autowired
    private lateinit var userService: UserService

    override fun findByToken(token: String?): RefreshToken? {
        token ?: return null
        return refreshTokenRepo.findByToken(token)
    }

    override fun createRefreshToken(userId: String?): RefreshToken? {
        userId ?: return null
        //ensure the userId exists before issuing a new refreshToken
        userService.findUserById(userId) ?: run {
            throw TokenRefreshException("new token", "User $userId does not exist")
        }
        val refreshToken = RefreshToken(
            userId = userId,
            expiryDate = LocalDateTime.now()
                .plusMinutes(refreshTokenExpirationHours.toLong())
        )
        return refreshTokenRepo.save(refreshToken)
    }

    override fun deleteToken(refreshToken: String) {
        refreshTokenRepo.deleteToken(refreshToken)
    }

    override fun refreshTokenOwner(refreshToken: String): MNUser? {
        val tokenData = refreshTokenRepo.findByToken(refreshToken)
        tokenData ?: return null
        return userService.findUserById(tokenData.userId)
    }

    override fun isTokenNotExpiredAndValid(refreshToken: String): Boolean {
        val tokenObj = this.findByToken(refreshToken) ?: return false
        return !isTokenExpired(tokenObj)
    }

    override fun isTokenExpired(token: RefreshToken?): Boolean {
        token ?: return true
        val now = LocalDateTime.now()
        return token.expiryDate.isBefore(now)
    }

    override fun deleteTokensForUserId(userId: String?) {
        userId ?: return
        refreshTokenRepo.deleteTokensForUser(userId)
    }

    override fun invalidateRefreshTokenIfAuthorised(
        refreshToken: String,
        requester: String,
        isAdmin: Boolean): Boolean {
        val isAuthorised = isRequesterAuthorizedToDeleteRefreshToken(
            refreshToken = refreshToken,
            requesterUserId = requester,
            isAdmin = isAdmin
        )
        if (!isAuthorised) {
            return false
        }
        this.deleteToken(refreshToken)
        return true
    }

    private fun isRequesterAuthorizedToDeleteRefreshToken(
        refreshToken: String,
        requesterUserId: String,
        isAdmin: Boolean
    ): Boolean {
        if (isAdmin) {
            return true
        }
        val userForRefreshToken = this.refreshTokenOwner(refreshToken)
        userForRefreshToken ?: run {
            return false
        }
        if (userForRefreshToken.id != requesterUserId) {
            return false
        }

        return true
    }
}