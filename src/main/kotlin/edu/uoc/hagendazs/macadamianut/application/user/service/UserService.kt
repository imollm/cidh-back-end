package edu.uoc.hagendazs.macadamianut.application.user.service

import edu.uoc.hagendazs.macadamianut.application.user.entrypoint.input.UpdateUserRequest
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.MNUser

interface UserService {
    fun findAll(): Collection<MNUser>
    fun updatePerson(resolvedUserId: String, userData: UpdateUserRequest): MNUser?
    fun findUserById(resolvedPersonId: String): MNUser?
    fun createUser(user: MNUser): MNUser?
}