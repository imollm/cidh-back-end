package edu.uoc.hagendazs.macadamianut.application.user.model.dataClass

import edu.uoc.hagendazs.macadamianut.common.kotlin.valueOfIgnoreCase

enum class RoleEnum {
    User,
    Administrator,
    SuperAdmin;

    companion object {

        fun determineHigherAuthority(
            authorities: List<String>
        ): RoleEnum {
            val authoritiesAsRoles = authorities.mapNotNull {
                valueOfIgnoreCase<RoleEnum>(it)
            }

            if (authoritiesAsRoles.contains(SuperAdmin)) {
                return SuperAdmin
            }

            if (authoritiesAsRoles.contains(Administrator)) {
                return Administrator
            }
            return User
        }
    }
}