package edu.uoc.hagendazs.macadamianut.application.user.entrypoint

import edu.uoc.hagendazs.macadamianut.application.user.entrypoint.input.CreateUserRequest
import edu.uoc.hagendazs.macadamianut.application.user.entrypoint.input.UpdateUserRequest
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.MNUser
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.RoleEnum
import edu.uoc.hagendazs.macadamianut.application.user.service.UserService
import edu.uoc.hagendazs.macadamianut.application.user.service.helper.JwtHelper
import edu.uoc.hagendazs.macadamianut.common.HTTPMessages.Companion.FORBIDDEN_USER_ADMIN_ACCESS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.net.URI
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@Controller
class UserController {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @PostMapping(value = ["/api/v1/users"])
    fun createUser(
        @RequestBody newUserReq: CreateUserRequest,
        request: HttpServletRequest,
        jwtToken: Authentication?
    ): ResponseEntity<MNUser> {

        val internalUser = newUserReq.toInternalUserModel(passwordEncoder)
        val resolvedRole = resolveAndValidatePermissionsForRole(newUserReq.role, jwtToken)
        val user = userService.createUser(internalUser, resolvedRole)
        user ?: run {
            throw throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Unable to create user with given payload: $newUserReq"
            )
        }
        val userUriString = request.requestURL.toString().plus("/${user.id}")
        val personUri = URI.create(userUriString)
        return ResponseEntity.created(personUri).body(user)
    }

    // ############# LIST ALL USERS #############
    @PreAuthorize("hasAnyRole('SUPERADMIN')")
    @RequestMapping(value = ["/api/v1/users"], method = [(RequestMethod.GET)])
    fun getAllUsers(
        @RequestParam role: Array<RoleEnum>?,
    ): ResponseEntity<Collection<MNUser>> {
        val persons = userService.findAll(roleFilter = role?.asList() ?: RoleEnum.values().asList())
        return ResponseEntity.ok(persons)
    }

    // ############# GET SINGLE USER #############
    @PreAuthorize("#userId == authentication.principal.claims['username'] or hasAnyRole('ADMIN', 'SUPERADMIN') or #userId == null")
    @GetMapping(value = ["/api/v1/users/{userId}", "/api/v1/users/me"])
    fun getUser(
        @PathVariable("userId") userId: String?,
        jwtToken: Authentication
    ): ResponseEntity<MNUser> {
        val resolvedPersonId = resolveUserId(userId, jwtToken)
        val user = userService.findUserById(resolvedPersonId)
        return ResponseEntity.ok(user)
    }

    // ############# UPDATE USER #############
    @PreAuthorize("#userId == authentication.principal.claims['username'] or hasAnyRole('ADMIN', 'SUPERADMIN') or #userId == null")
    @PostMapping(value = ["/api/v1/users/{userId}", "/api/v1/users/me"])
    fun updateUser(
        @PathVariable("userId") userId: String?,
        @RequestBody @Valid personData: UpdateUserRequest,
        jwtToken: Authentication
    ): ResponseEntity<MNUser> {
        validateUpdateUserAuthorization(userId, personData, jwtToken.name)
        val resolvedPersonId = resolveUserId(userId, jwtToken)
        val updatedPerson = userService.updatePerson(resolvedPersonId, personData)
        return ResponseEntity.ok(updatedPerson)
    }

    companion object {

        fun resolveUserId(personId: String?, token: Authentication?): String {
            return personId ?: token?.name ?: run {
                throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
            }
        }

        /**
         * Checks the requested user id in the payload is consistent with the POST payload.
         * 1 - If the requested user id in the query param to be updated matches with the jwt id -> OK
         * 2 - If the userId in the query param is "me", check that the payload matches with JWT token
         * Throw otherwise
         */
        private fun validateUpdateUserAuthorization(
            personId: String?,
            updateRequest: UpdateUserRequest,
            jwtTokenPersonId: String?,
        ) {
            val requestedTargetPersonId = personId ?: jwtTokenPersonId ?: kotlin.run {
                throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
            }
            if (updateRequest.id == null || requestedTargetPersonId == updateRequest.id) {
                return
            }
            var userMeMessage = ""
            if (personId == null) {
                userMeMessage = "(using the 'me' userId alias)"
            }
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Url userId '$requestedTargetPersonId' $userMeMessage does not match " +
                        "with the payload user id: '${updateRequest.id}'"
            )
        }

        private fun resolveAndValidatePermissionsForRole(role: RoleEnum?,
                                                         jwtToken: Authentication?): RoleEnum {
            role ?: return RoleEnum.User
            when (role) {
                RoleEnum.User -> return RoleEnum.User
                RoleEnum.Admin -> {
                    jwtToken ?: run {
                        throw ResponseStatusException(HttpStatus.FORBIDDEN, "Creating Users with Admin role requires a JWT Token")
                    }
                    val requesterRoles = JwtHelper.rolesFromJwtToken(jwtToken)
                    if (requesterRoles.contains(RoleEnum.SuperAdmin)) {
                        return RoleEnum.Admin
                    }
                }
                RoleEnum.SuperAdmin -> throw ResponseStatusException(HttpStatus.FORBIDDEN, FORBIDDEN_USER_ADMIN_ACCESS)
            }
            throw ResponseStatusException(HttpStatus.FORBIDDEN, FORBIDDEN_USER_ADMIN_ACCESS)
        }
    }

}