package edu.uoc.hagendazs.macadamianut.application.user.model.repo

import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.MNUser
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.UserRole

interface UserRepo {
    fun findById(userId: String?): MNUser?
    fun findUserByEmail(email: String): MNUser?
    fun userRolesForUserId(userId: String): Iterable<UserRole>
}