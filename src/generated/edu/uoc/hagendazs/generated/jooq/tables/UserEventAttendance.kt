/*
 * This file is generated by jOOQ.
 */
package edu.uoc.hagendazs.generated.jooq.tables


import edu.uoc.hagendazs.generated.jooq.Public
import edu.uoc.hagendazs.generated.jooq.keys.PK_USER_EVENT_ATTENDANCE
import edu.uoc.hagendazs.generated.jooq.keys.USER_EVENT_ATTENDANCE__FK_USER_EVENT_COMMENT_EVENT_ID
import edu.uoc.hagendazs.generated.jooq.keys.USER_EVENT_ATTENDANCE__FK_USER_EVENT_COMMENT_USER_ID
import edu.uoc.hagendazs.generated.jooq.tables.records.UserEventAttendanceRecord

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
open class UserEventAttendance(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, UserEventAttendanceRecord>?,
    aliased: Table<UserEventAttendanceRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<UserEventAttendanceRecord>(
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
         * The reference instance of <code>public.user_event_attendance</code>
         */
        val USER_EVENT_ATTENDANCE = UserEventAttendance()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<UserEventAttendanceRecord> = UserEventAttendanceRecord::class.java

    /**
     * The column <code>public.user_event_attendance.user_id</code>.
     */
    val USER_ID: TableField<UserEventAttendanceRecord, String?> = createField(DSL.name("user_id"), SQLDataType.VARCHAR.nullable(false), this, "")

    /**
     * The column <code>public.user_event_attendance.event_id</code>.
     */
    val EVENT_ID: TableField<UserEventAttendanceRecord, String?> = createField(DSL.name("event_id"), SQLDataType.VARCHAR.nullable(false), this, "")

    /**
     * The column <code>public.user_event_attendance.created_at</code>.
     */
    val CREATED_AT: TableField<UserEventAttendanceRecord, LocalDateTime?> = createField(DSL.name("created_at"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "")

    private constructor(alias: Name, aliased: Table<UserEventAttendanceRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<UserEventAttendanceRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>public.user_event_attendance</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>public.user_event_attendance</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>public.user_event_attendance</code> table reference
     */
    constructor(): this(DSL.name("user_event_attendance"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, UserEventAttendanceRecord>): this(Internal.createPathAlias(child, key), child, key, USER_EVENT_ATTENDANCE, null)
    override fun getSchema(): Schema = Public.PUBLIC
    override fun getPrimaryKey(): UniqueKey<UserEventAttendanceRecord> = PK_USER_EVENT_ATTENDANCE
    override fun getKeys(): List<UniqueKey<UserEventAttendanceRecord>> = listOf(PK_USER_EVENT_ATTENDANCE)
    override fun getReferences(): List<ForeignKey<UserEventAttendanceRecord, *>> = listOf(USER_EVENT_ATTENDANCE__FK_USER_EVENT_COMMENT_USER_ID, USER_EVENT_ATTENDANCE__FK_USER_EVENT_COMMENT_EVENT_ID)

    private lateinit var _user: User
    private lateinit var _event: Event
    fun user(): User {
        if (!this::_user.isInitialized)
            _user = User(this, USER_EVENT_ATTENDANCE__FK_USER_EVENT_COMMENT_USER_ID)

        return _user;
    }
    fun event(): Event {
        if (!this::_event.isInitialized)
            _event = Event(this, USER_EVENT_ATTENDANCE__FK_USER_EVENT_COMMENT_EVENT_ID)

        return _event;
    }
    override fun `as`(alias: String): UserEventAttendance = UserEventAttendance(DSL.name(alias), this)
    override fun `as`(alias: Name): UserEventAttendance = UserEventAttendance(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): UserEventAttendance = UserEventAttendance(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): UserEventAttendance = UserEventAttendance(name, null)

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row3<String?, String?, LocalDateTime?> = super.fieldsRow() as Row3<String?, String?, LocalDateTime?>
}