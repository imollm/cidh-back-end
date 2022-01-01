package edu.uoc.hagendazs.macadamianut.application.user.utils

import edu.uoc.hagendazs.macadamianut.application.user.entrypoint.input.UpdateUserRequest
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.MNUser
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.RoleEnum
import edu.uoc.hagendazs.macadamianut.application.user.service.helper.JwtHelper
import edu.uoc.hagendazs.macadamianut.common.HTTPMessages
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.web.server.ResponseStatusException

class UserUtils {
    companion object {

        fun fullNameForUser(
            usersMapByUserId: Map<String, MNUser>,
            userId: String?,
        ): String {
            userId ?: return "Anonymous"
            val user = usersMapByUserId[userId] ?: return ""
            return "${user.firstName} ${user.lastName}"
        }

        fun resolveUserId(userId: String?, token: Authentication?): String {
            return userId ?: token?.name ?: run {
                throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
            }
        }

        /**
         * Checks the requested user id in the payload is consistent with the POST payload.
         * 1 - If the requested user id in the query param to be updated matches with the jwt id -> OK
         * 2 - If the userId in the query param is "me", check that the payload matches with JWT token
         * Throw otherwise
         */
        fun validateUpdateUserAuthorization(
            userId: String?,
            updateRequest: UpdateUserRequest,
            jwtTokenUserId: String?,
        ) {
            val requestedTargetPersonId = userId ?: jwtTokenUserId ?: kotlin.run {
                throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
            }
            if (updateRequest.id == null || requestedTargetPersonId == updateRequest.id) {
                return
            }
            var userMeMessage = ""
            if (userId == null) {
                userMeMessage = "(using the 'me' userId alias)"
            }
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Url userId '$requestedTargetPersonId' $userMeMessage does not match " +
                        "with the payload user id: '${updateRequest.id}'"
            )
        }

        fun resolveAndValidatePermissionsForRole(
            role: RoleEnum?,
            jwtToken: Authentication?
        ): RoleEnum {
            role ?: return RoleEnum.User
            when (role) {
                RoleEnum.User -> return RoleEnum.User
                RoleEnum.Admin -> {
                    jwtToken ?: run {
                        throw ResponseStatusException(
                            HttpStatus.FORBIDDEN,
                            "Creating Users with Admin role requires a JWT Token"
                        )
                    }
                    val requesterRoles = JwtHelper.rolesFromJwtToken(jwtToken)
                    if (requesterRoles.contains(RoleEnum.SuperAdmin)) {
                        return RoleEnum.Admin
                    }
                }
                RoleEnum.SuperAdmin -> throw ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    HTTPMessages.FORBIDDEN_USER_ADMIN_ACCESS
                )
            }
            throw ResponseStatusException(HttpStatus.FORBIDDEN, HTTPMessages.FORBIDDEN_USER_ADMIN_ACCESS)
        }
    }
}