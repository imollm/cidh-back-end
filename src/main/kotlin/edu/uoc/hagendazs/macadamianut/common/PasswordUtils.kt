package edu.uoc.hagendazs.macadamianut.common

class PasswordUtils {

    companion object {
        private val passwordRegex = Regex("^(?=.*[A-Z])(?=.*[a-z]).{8,}\$")

        fun isPasswordSecure(password: String): Boolean {
            return passwordRegex.matches(password)
        }

        fun passwordRegex(): String {
            return passwordRegex.pattern
        }

        fun passwordRegexDescription(): String {
            return "At 8 characters long, at least one upper case, at least one lower case"
        }
    }
}