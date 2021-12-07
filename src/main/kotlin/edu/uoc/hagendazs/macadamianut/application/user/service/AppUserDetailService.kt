package edu.uoc.hagendazs.macadamianut.application.user.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

interface AppUserDetailService : UserDetailsService {

    fun loadUserById(userId: String?): UserDetails

}