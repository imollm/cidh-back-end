package edu.uoc.hagendazs.macadamianut.application.user.entrypoint.input

import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.MNUser
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.RoleEnum
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.SystemLanguage
import edu.uoc.hagendazs.macadamianut.application.user.service.exceptions.WeakPasswordException
import edu.uoc.hagendazs.macadamianut.common.PasswordUtils
import org.springframework.security.crypto.password.PasswordEncoder

data class CreateUserRequest(
    var id: String?,

    var email: String,
    val password: String,

    //optional fields
    var firstName: String?,
    var lastName: String?,
    var fiscalId: String?,
    var address: String?,
    var preferredLanguage: SystemLanguage?,
    var role: RoleEnum?,
) {

    fun toInternalUserModel(
        passwordEncoder: PasswordEncoder
    ): MNUser {
        if (!PasswordUtils.isPasswordSecure(password)) {
            throw WeakPasswordException(
                "Password does not meet minimum acceptance criteria " + PasswordUtils.passwordRegexDescription()
            )
        }

        val hashedSaltedPassword = passwordEncoder.encode(password)
        return MNUser(
            email = email,
            password = hashedSaltedPassword,
            firstName = firstName,
            lastName = lastName,
            fiscalId = fiscalId,
            address = address,
            preferredLanguage = preferredLanguage ?: SystemLanguage.English,
        )
    }
}
