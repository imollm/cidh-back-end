package edu.uoc.hagendazs.macadamianut.application.user.service.impl

import edu.uoc.hagendazs.macadamianut.application.user.entrypoint.input.UserUpdateReq
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.MNUser
import edu.uoc.hagendazs.macadamianut.application.user.model.repo.UserRepo
import edu.uoc.hagendazs.macadamianut.application.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserServiceImpl: UserService {

    @Autowired
    lateinit var userRepo: UserRepo

    override fun findAll(): Collection<MNUser> {
        return userRepo.findAll()
    }

    override fun updatePerson(resolvedUserId: String, userData: UserUpdateReq): MNUser? {
        val user = userRepo.findById(resolvedUserId) ?: kotlin.run {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found") //TODO Move HTTP Exceptions to Controller
        }
        val updatedUser = updatedUserFromRequest(user, userData)
    }

    override fun findUserById(resolvedPersonId: String): MNUser? {
        TODO("Not yet implemented")
    }

    override fun createUser(email: String, plainTextPassword: String): MNUser {
        TODO("Not yet implemented")
    }

    companion object {
        fun updatedUserFromRequest(user: MNUser, userData: UserUpdateReq): MNUser {
            return user.copy(
                email = userData.email,

            )
            TODO("Not yet implemented")
        }

    }
}