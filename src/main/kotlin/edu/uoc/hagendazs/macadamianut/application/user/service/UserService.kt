package edu.uoc.hagendazs.macadamianut.application.user.service

import edu.uoc.hagendazs.macadamianut.application.user.entrypoint.input.UserUpdateReq
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.MNUser

interface UserService {
    fun findAll(): Collection<MNUser>
    fun updatePerson(resolvedUserId: String, personData: UserUpdateReq): MNUser?
    fun findUserById(resolvedPersonId: String): MNUser?
    fun createUser(email: String, plainTextPassword: String): MNUser
}