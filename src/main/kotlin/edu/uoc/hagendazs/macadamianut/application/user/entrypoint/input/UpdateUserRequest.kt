package edu.uoc.hagendazs.macadamianut.application.user.entrypoint.input

import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.SystemLanguage
import edu.uoc.hagendazs.macadamianut.common.StringUtils
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class UpdateUserRequest(
    val id: String? = null,

    var email: String,
    val firstName: String?,
    val lastName: String?,
    val nif: String?,
    val address: String?,
    val preferredLanguage: SystemLanguage,
) {
    init {
        email = StringUtils.validateAndLowerCaseEmail(email)
    }
}