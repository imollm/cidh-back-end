/*
 * This file is generated by jOOQ.
 */
package edu.uoc.hagendazs.generated.jooq.tables


import edu.uoc.hagendazs.generated.jooq.Public
import edu.uoc.hagendazs.generated.jooq.enums.Systemlanguagetype
import edu.uoc.hagendazs.generated.jooq.keys.USER_EMAIL_KEY
import edu.uoc.hagendazs.generated.jooq.keys.USER_PKEY
import edu.uoc.hagendazs.generated.jooq.tables.records.UserRecord
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.SystemLanguage

import java.time.LocalDateTime

import kotlin.collections.List

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Name
import org.jooq.Record
import org.jooq.Row12
import org.jooq.Schema
import org.jooq.Table
import org.jooq.TableField
import org.jooq.TableOptions
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.EnumConverter
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class User(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, UserRecord>?,
    aliased: Table<UserRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<UserRecord>(
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
         * The reference instance of <code>public.user</code>
         */
        val USER = User()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<UserRecord> = UserRecord::class.java

    /**
     * The column <code>public.user.id</code>.
     */
    val ID: TableField<UserRecord, String?> = createField(DSL.name("id"), SQLDataType.VARCHAR.nullable(false), this, "")

    /**
     * The column <code>public.user.first_name</code>.
     */
    val FIRST_NAME: TableField<UserRecord, String?> = createField(DSL.name("first_name"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>public.user.last_name</code>.
     */
    val LAST_NAME: TableField<UserRecord, String?> = createField(DSL.name("last_name"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>public.user.fiscal_id</code>.
     */
    val FISCAL_ID: TableField<UserRecord, String?> = createField(DSL.name("fiscal_id"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>public.user.address</code>.
     */
    val ADDRESS: TableField<UserRecord, String?> = createField(DSL.name("address"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>public.user.email</code>.
     */
    val EMAIL: TableField<UserRecord, String?> = createField(DSL.name("email"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>public.user.email_token</code>.
     */
    val EMAIL_TOKEN: TableField<UserRecord, String?> = createField(DSL.name("email_token"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>public.user.is_valid_email</code>.
     */
    val IS_VALID_EMAIL: TableField<UserRecord, Boolean?> = createField(DSL.name("is_valid_email"), SQLDataType.BOOLEAN.defaultValue(DSL.field("false", SQLDataType.BOOLEAN)), this, "")

    /**
     * The column <code>public.user.preferred_language</code>.
     */
    val PREFERRED_LANGUAGE: TableField<UserRecord, SystemLanguage?> = createField(DSL.name("preferred_language"), SQLDataType.VARCHAR.nullable(false).defaultValue(DSL.field("'English'::systemlanguagetype", SQLDataType.VARCHAR)).asEnumDataType(edu.uoc.hagendazs.generated.jooq.enums.Systemlanguagetype::class.java), this, "", EnumConverter<Systemlanguagetype, SystemLanguage>(Systemlanguagetype::class.java, SystemLanguage::class.java))

    /**
     * The column <code>public.user.password</code>.
     */
    val PASSWORD: TableField<UserRecord, String?> = createField(DSL.name("password"), SQLDataType.VARCHAR.nullable(false), this, "")

    /**
     * The column <code>public.user.deleted_at</code>.
     */
    val DELETED_AT: TableField<UserRecord, LocalDateTime?> = createField(DSL.name("deleted_at"), SQLDataType.LOCALDATETIME(6), this, "")

    /**
     * The column <code>public.user.created_at</code>.
     */
    val CREATED_AT: TableField<UserRecord, LocalDateTime?> = createField(DSL.name("created_at"), SQLDataType.LOCALDATETIME(6), this, "")

    private constructor(alias: Name, aliased: Table<UserRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<UserRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>public.user</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>public.user</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>public.user</code> table reference
     */
    constructor(): this(DSL.name("user"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, UserRecord>): this(Internal.createPathAlias(child, key), child, key, USER, null)
    override fun getSchema(): Schema = Public.PUBLIC
    override fun getPrimaryKey(): UniqueKey<UserRecord> = USER_PKEY
    override fun getKeys(): List<UniqueKey<UserRecord>> = listOf(USER_PKEY, USER_EMAIL_KEY)
    override fun `as`(alias: String): User = User(DSL.name(alias), this)
    override fun `as`(alias: Name): User = User(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): User = User(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): User = User(name, null)

    // -------------------------------------------------------------------------
    // Row12 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row12<String?, String?, String?, String?, String?, String?, String?, Boolean?, SystemLanguage?, String?, LocalDateTime?, LocalDateTime?> = super.fieldsRow() as Row12<String?, String?, String?, String?, String?, String?, String?, Boolean?, SystemLanguage?, String?, LocalDateTime?, LocalDateTime?>
}
