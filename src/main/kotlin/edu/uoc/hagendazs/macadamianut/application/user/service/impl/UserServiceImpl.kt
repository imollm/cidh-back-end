package edu.uoc.hagendazs.macadamianut.application.user.service.impl

import edu.uoc.hagendazs.macadamianut.application.user.entrypoint.input.UpdateUserRequest
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.MNUser
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.RoleEnum
import edu.uoc.hagendazs.macadamianut.application.user.model.repo.UserRepo
import edu.uoc.hagendazs.macadamianut.application.user.service.UserService
import edu.uoc.hagendazs.macadamianut.application.user.service.exceptions.UserAlreadyExistsException
import edu.uoc.hagendazs.macadamianut.application.user.service.exceptions.UserNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {

    @Autowired
    lateinit var userRepo: UserRepo

    @Autowired
    lateinit var userService: UserService

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    override fun findAll(roleFilter: Collection<RoleEnum>): Collection<MNUser> {
        return userRepo.findAll(roleFilter)
    }

    override fun updatePerson(resolvedUserId: String, userData: UpdateUserRequest): MNUser? {
        val user = userRepo.findById(resolvedUserId) ?: kotlin.run {
            throw UserNotFoundException("User with id $resolvedUserId not found.")
        }
        val updatedUser = updatedUserFromRequest(user, userData)
        return userRepo.updateUser(updatedUser)
    }

    override fun findUserById(resolvedPersonId: String): MNUser? {
        return userRepo.findById(resolvedPersonId)
    }

    override fun createUser(user: MNUser, role: RoleEnum): MNUser? {
        if (userRepo.existsByEmail(user.email)) {
            throw UserAlreadyExistsException("Unable to create user with given email: ${user.email}")
        }
        return userRepo.createUser(user, role)
    }

    override fun permissionsForRole(role: RoleEnum): String? {
        return userRepo.permissionsForRole(role)
    }

    companion object {
        fun updatedUserFromRequest(user: MNUser, userData: UpdateUserRequest): MNUser {
            return user.copy(
                email = userData.email,
                firstName = userData.firstName,
                lastName = userData.lastName,
                fiscalId = userData.fiscalId,
                address = userData.address,
                preferredLanguage = userData.preferredLanguage,
            )
        }

    }
}