package edu.uoc.hagendazs.macadamianut.common

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class StringUtils {
    companion object {

        private val emailRegex = Regex(".+@.+\\..+")

        fun validateAndLowerCaseEmail(email: String): String {
            require(emailRegex.matches(email)) {
                throw ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The received email address is not valid: \"$email\""
                )
            }
            return email.toLowerCase()
        }

        fun checkEmailRegex(email: String): Boolean {
            return emailRegex.matches(email)
        }
    }
}
