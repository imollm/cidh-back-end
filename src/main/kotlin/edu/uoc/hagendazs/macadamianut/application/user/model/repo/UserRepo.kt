package edu.uoc.hagendazs.macadamianut.application.user.model.repo

import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.MNUser
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.RoleEnum
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.UserRole

interface UserRepo {
    fun findById(userId: String?): MNUser?
    fun findUserByEmail(email: String): MNUser?
    fun findAll(roleFilter: Collection<RoleEnum>): Collection<MNUser>
    fun updateUser(user: MNUser?): MNUser?
    fun userRolesForUserId(userId: String): Iterable<UserRole>
    fun changePassword(user: MNUser, newPassword: String)
    fun deleteUser(userId: String)
    fun existsByEmail(email: String): Boolean
    fun createUser(newUser: MNUser, role: RoleEnum): MNUser?
    fun permissionsForRole(role: RoleEnum): String?
}