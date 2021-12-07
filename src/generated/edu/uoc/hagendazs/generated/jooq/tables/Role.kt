/*
 * This file is generated by jOOQ.
 */
package edu.uoc.hagendazs.generated.jooq.tables


import edu.uoc.hagendazs.generated.jooq.Public
import edu.uoc.hagendazs.generated.jooq.keys.ROLE_PKEY
import edu.uoc.hagendazs.generated.jooq.tables.records.RoleRecord

import kotlin.collections.List

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.JSON
import org.jooq.Name
import org.jooq.Record
import org.jooq.Row3
import org.jooq.Schema
import org.jooq.Table
import org.jooq.TableField
import org.jooq.TableOptions
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class Role(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, RoleRecord>?,
    aliased: Table<RoleRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<RoleRecord>(
    alias,
    Public.PUBLIC,
    child,
    path,
    aliased,
    parameters,
    DSL.comment(""),
    TableOptions.table()
) {
    companion object {

        /**
         * The reference instance of <code>public.role</code>
         */
        val ROLE = Role()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<RoleRecord> = RoleRecord::class.java

    /**
     * The column <code>public.role.id</code>.
     */
    val ID: TableField<RoleRecord, String?> = createField(DSL.name("id"), SQLDataType.VARCHAR.nullable(false), this, "")

    /**
     * The column <code>public.role.role_name</code>.
     */
    val ROLE_NAME: TableField<RoleRecord, String?> = createField(DSL.name("role_name"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>public.role.role_definition_json</code>.
     */
    val ROLE_DEFINITION_JSON: TableField<RoleRecord, JSON?> = createField(DSL.name("role_definition_json"), SQLDataType.JSON, this, "")

    private constructor(alias: Name, aliased: Table<RoleRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<RoleRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>public.role</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>public.role</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>public.role</code> table reference
     */
    constructor(): this(DSL.name("role"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, RoleRecord>): this(Internal.createPathAlias(child, key), child, key, ROLE, null)
    override fun getSchema(): Schema = Public.PUBLIC
    override fun getPrimaryKey(): UniqueKey<RoleRecord> = ROLE_PKEY
    override fun getKeys(): List<UniqueKey<RoleRecord>> = listOf(ROLE_PKEY)
    override fun `as`(alias: String): Role = Role(DSL.name(alias), this)
    override fun `as`(alias: Name): Role = Role(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): Role = Role(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): Role = Role(name, null)

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row3<String?, String?, JSON?> = super.fieldsRow() as Row3<String?, String?, JSON?>
}
