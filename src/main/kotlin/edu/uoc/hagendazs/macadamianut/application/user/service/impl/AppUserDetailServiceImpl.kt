package edu.uoc.hagendazs.macadamianut.application.user.service.impl

import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.MNUser
import edu.uoc.hagendazs.macadamianut.application.user.model.repo.UserRepo
import edu.uoc.hagendazs.macadamianut.application.user.service.AppUserDetailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AppUserDetailServiceImpl: AppUserDetailService {

    @Autowired
    private lateinit var userRepo: UserRepo

    override fun loadUserById(userId: String?): UserDetails {
        val user = userRepo.findById(userId) ?: run {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid person Id")
        }
        val authorities = personAuthorities(user)
        return createUserDetails(user, authorities)
    }

    override fun loadUserByUsername(email: String?): UserDetails {
        email ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Provided user name is null")
        val person = userRepo.findUserByEmail(email) ?: throw UsernameNotFoundException("User $email not found")
        val authorities = personAuthorities(person)
        return createUserDetails(person, authorities)
    }

    private fun personAuthorities(user: MNUser) =
        userRepo.userRolesForUserId(user.id).map { SimpleGrantedAuthority(it.role) }.toList()

    private fun createUserDetails(
        user: MNUser,
        authorities: List<SimpleGrantedAuthority>
    ) = User.withUsername(user.id)
        .authorities(authorities)
        .password(user.password)
        .accountExpired(false)
        .accountLocked(false)
        .build()

}