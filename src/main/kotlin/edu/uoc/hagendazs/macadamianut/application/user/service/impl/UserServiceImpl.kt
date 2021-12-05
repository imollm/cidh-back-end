package edu.uoc.hagendazs.macadamianut.application.user.service.impl

import edu.uoc.hagendazs.macadamianut.application.user.entrypoint.input.UserUpdateReq
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.MNUser
import edu.uoc.hagendazs.macadamianut.application.user.model.repo.UserRepo
import edu.uoc.hagendazs.macadamianut.application.user.service.UserService
import edu.uoc.hagendazs.macadamianut.common.PasswordUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Service
class UserServiceImpl: UserService {

    @Autowired
    lateinit var userRepo: UserRepo

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    override fun findAll(): Collection<MNUser> {
        return userRepo.findAll()
    }

    override fun updatePerson(resolvedUserId: String, userData: UserUpdateReq): MNUser? {
        val user = userRepo.findById(resolvedUserId) ?: kotlin.run {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found") //TODO Move HTTP Exceptions to Controller
        }
        val updatedUser = updatedUserFromRequest(user, userData)
        return userRepo.updateUser(updatedUser)
    }

    override fun findUserById(resolvedPersonId: String): MNUser? {
        return userRepo.findById(resolvedPersonId)
    }

    override fun createUser(email: String, plainTextPassword: String): MNUser? {
        if (userRepo.existsByEmail(email)) {
            throw IllegalStateException("This E-Mail already exists")
        }
        if (!PasswordUtils.isPasswordSecure(plainTextPassword)) {
            throw IllegalArgumentException("This Password doesn't meet the criteria")
        }

        val securePassword = passwordEncoder.encode(plainTextPassword)

        val newUser = MNUser(
            email = email,
            password = securePassword,
        )
        return userRepo.createUser(newUser)
    }

    companion object {
        fun updatedUserFromRequest(user: MNUser, userData: UserUpdateReq): MNUser {
            return user.copy(
                email = userData.email,
                firstName = userData.firstName,
                lastName = userData.lastName,
                nif = userData.nif,
                address = userData.address,
                preferredLanguage = userData.preferredLanguage,
            )
        }

    }
}