package edu.uoc.hagendazs.macadamianut.application.user.service

import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.MNUser

interface UserService {
    fun findAll(): Collection<MNUser>
}