package edu.uoc.hagendazs.macadamianut.common

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class StringUtils {
    companion object {

        private val emailRegex = Regex(".+@.+\\..+")

        fun validateAndLowerCaseEmail(email: String?): String {
            require(emailRegex.matches(email.toString())) {
                throw ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The received email address is not valid: \"$email\""
                )
            }
            return email?.lowercase() ?: ""
        }

        fun checkEmailRegex(email: String?): Boolean {
            email ?: return false
            return emailRegex.matches(email)
        }
    }
}
