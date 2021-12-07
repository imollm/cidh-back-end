/*
 * This file is generated by jOOQ.
 */
package edu.uoc.hagendazs.generated.jooq.tables


import edu.uoc.hagendazs.generated.jooq.Public
import edu.uoc.hagendazs.generated.jooq.keys.REFRESH_TOKEN_PKEY
import edu.uoc.hagendazs.generated.jooq.keys.REFRESH_TOKEN__REFRESH_TOKEN_USER_ID
import edu.uoc.hagendazs.generated.jooq.tables.records.RefreshTokenRecord

import java.time.LocalDateTime

import kotlin.collections.List

import org.jooq.Field
import org.jooq.ForeignKey
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
open class RefreshToken(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, RefreshTokenRecord>?,
    aliased: Table<RefreshTokenRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<RefreshTokenRecord>(
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
         * The reference instance of <code>public.refresh_token</code>
         */
        val REFRESH_TOKEN = RefreshToken()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<RefreshTokenRecord> = RefreshTokenRecord::class.java

    /**
     * The column <code>public.refresh_token.id</code>.
     */
    val ID: TableField<RefreshTokenRecord, String?> = createField(DSL.name("id"), SQLDataType.VARCHAR.nullable(false), this, "")

    /**
     * The column <code>public.refresh_token.user_id</code>.
     */
    val USER_ID: TableField<RefreshTokenRecord, String?> = createField(DSL.name("user_id"), SQLDataType.VARCHAR.nullable(false), this, "")

    /**
     * The column <code>public.refresh_token.expiry_date</code>.
     */
    val EXPIRY_DATE: TableField<RefreshTokenRecord, LocalDateTime?> = createField(DSL.name("expiry_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "")

    private constructor(alias: Name, aliased: Table<RefreshTokenRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<RefreshTokenRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>public.refresh_token</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>public.refresh_token</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>public.refresh_token</code> table reference
     */
    constructor(): this(DSL.name("refresh_token"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, RefreshTokenRecord>): this(Internal.createPathAlias(child, key), child, key, REFRESH_TOKEN, null)
    override fun getSchema(): Schema = Public.PUBLIC
    override fun getPrimaryKey(): UniqueKey<RefreshTokenRecord> = REFRESH_TOKEN_PKEY
    override fun getKeys(): List<UniqueKey<RefreshTokenRecord>> = listOf(REFRESH_TOKEN_PKEY)
    override fun getReferences(): List<ForeignKey<RefreshTokenRecord, *>> = listOf(REFRESH_TOKEN__REFRESH_TOKEN_USER_ID)

    private lateinit var _user: User
    fun user(): User {
        if (!this::_user.isInitialized)
            _user = User(this, REFRESH_TOKEN__REFRESH_TOKEN_USER_ID)

        return _user;
    }
    override fun `as`(alias: String): RefreshToken = RefreshToken(DSL.name(alias), this)
    override fun `as`(alias: Name): RefreshToken = RefreshToken(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): RefreshToken = RefreshToken(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): RefreshToken = RefreshToken(name, null)

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row3<String?, String?, LocalDateTime?> = super.fieldsRow() as Row3<String?, String?, LocalDateTime?>
}
