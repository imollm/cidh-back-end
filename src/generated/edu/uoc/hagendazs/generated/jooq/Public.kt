/*
 * This file is generated by jOOQ.
 */
package edu.uoc.hagendazs.generated.jooq


import edu.uoc.hagendazs.generated.jooq.tables.FlywaySchemaHistory
import edu.uoc.hagendazs.generated.jooq.tables.PasswordReset
import edu.uoc.hagendazs.generated.jooq.tables.Role
import edu.uoc.hagendazs.generated.jooq.tables.User
import edu.uoc.hagendazs.generated.jooq.tables.UserRole

import kotlin.collections.List

import org.jooq.Catalog
import org.jooq.Table
import org.jooq.impl.SchemaImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class Public : SchemaImpl("public", DefaultCatalog.DEFAULT_CATALOG) {
    companion object {

        /**
         * The reference instance of <code>public</code>
         */
        val PUBLIC = Public()
    }

    /**
     * The table <code>public.flyway_schema_history</code>.
     */
    val FLYWAY_SCHEMA_HISTORY get() = FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY

    /**
     * The table <code>public.password_reset</code>.
     */
    val PASSWORD_RESET get() = PasswordReset.PASSWORD_RESET

    /**
     * The table <code>public.role</code>.
     */
    val ROLE get() = Role.ROLE

    /**
     * The table <code>public.user</code>.
     */
    val USER get() = User.USER

    /**
     * The table <code>public.user_role</code>.
     */
    val USER_ROLE get() = UserRole.USER_ROLE

    override fun getCatalog(): Catalog = DefaultCatalog.DEFAULT_CATALOG

    override fun getTables(): List<Table<*>> = listOf(
        FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY,
        PasswordReset.PASSWORD_RESET,
        Role.ROLE,
        User.USER,
        UserRole.USER_ROLE
    )
}