package edu.uoc.hagendazs.macadamianut.application.user.entrypoint.input

import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.SystemLanguage
import edu.uoc.hagendazs.macadamianut.common.StringUtils
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class UserUpdateReq(
    val id: String? = null,
    @field:NotEmpty(message = "Email cannot be empty")
    @field:Email var email: String,
    @field:NotBlank val firstName: String,
    @field:NotBlank val lastName: String,
    @field:NotBlank val nif: String,
    @field:NotBlank val address: String,
    @field:NotBlank val preferredLanguage: SystemLanguage,
) {
    init {
        email = StringUtils.validateAndLowerCaseEmail(email)
    }
}