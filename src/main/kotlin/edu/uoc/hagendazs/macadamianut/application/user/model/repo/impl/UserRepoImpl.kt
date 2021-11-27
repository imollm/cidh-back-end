package edu.uoc.hagendazs.macadamianut.application.user.model.repo.impl

import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.MNUser
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.UserRole
import edu.uoc.hagendazs.macadamianut.application.user.model.repo.UserRepo
import org.springframework.stereotype.Repository

@Repository
class UserRepoImpl: UserRepo {
    override fun findById(userId: String?): MNUser? {
        TODO("Not yet implemented")
    }

    override fun findUserByEmail(email: String): MNUser? {
        TODO("Not yet implemented")
    }

    override fun userRolesForUserId(userId: String): Iterable<UserRole> {
        TODO("Not yet implemented")
    }
}