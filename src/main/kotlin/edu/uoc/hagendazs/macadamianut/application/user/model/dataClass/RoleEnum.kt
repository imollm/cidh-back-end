package edu.uoc.hagendazs.macadamianut.application.user.model.dataClass

import edu.uoc.hagendazs.macadamianut.common.kotlin.valueOfIgnoreCase

enum class RoleEnum {
    User,
    Admin,
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

            if (authoritiesAsRoles.contains(Admin)) {
                return Admin
            }
            return User
        }
    }
}