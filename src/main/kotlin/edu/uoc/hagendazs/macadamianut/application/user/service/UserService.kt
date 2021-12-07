package edu.uoc.hagendazs.macadamianut.application.user.service

import edu.uoc.hagendazs.macadamianut.application.user.entrypoint.input.UpdateUserRequest
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.MNUser
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.RoleEnum

interface UserService {
    fun findAll(
        roleFilter: Collection<RoleEnum> = RoleEnum.values().asList()
    ): Collection<MNUser>
    fun updatePerson(resolvedUserId: String, userData: UpdateUserRequest): MNUser?
    fun findUserById(resolvedPersonId: String): MNUser?
    fun createUser(user: MNUser): MNUser?
    fun permissionsForRole(role: RoleEnum): String?
}