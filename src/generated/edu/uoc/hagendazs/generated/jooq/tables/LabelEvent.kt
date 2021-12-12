/*
 * This file is generated by jOOQ.
 */
package edu.uoc.hagendazs.generated.jooq.tables


import edu.uoc.hagendazs.generated.jooq.Public
import edu.uoc.hagendazs.generated.jooq.keys.LABEL_EVENT__LABEL_EVENT_CATEGORY_ID
import edu.uoc.hagendazs.generated.jooq.keys.LABEL_EVENT__LABEL_EVENT_EVENT_ID
import edu.uoc.hagendazs.generated.jooq.tables.records.LabelEventRecord

import kotlin.collections.List

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Name
import org.jooq.Record
import org.jooq.Row2
import org.jooq.Schema
import org.jooq.Table
import org.jooq.TableField
import org.jooq.TableOptions
import org.jooq.impl.DSL
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class LabelEvent(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, LabelEventRecord>?,
    aliased: Table<LabelEventRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<LabelEventRecord>(
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
         * The reference instance of <code>public.label_event</code>
         */
        val LABEL_EVENT = LabelEvent()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<LabelEventRecord> = LabelEventRecord::class.java

    /**
     * The column <code>public.label_event.event_id</code>.
     */
    val EVENT_ID: TableField<LabelEventRecord, String?> = createField(DSL.name("event_id"), SQLDataType.VARCHAR.nullable(false), this, "")

    /**
     * The column <code>public.label_event.category_id</code>.
     */
    val CATEGORY_ID: TableField<LabelEventRecord, String?> = createField(DSL.name("category_id"), SQLDataType.VARCHAR.nullable(false), this, "")

    private constructor(alias: Name, aliased: Table<LabelEventRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<LabelEventRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>public.label_event</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>public.label_event</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>public.label_event</code> table reference
     */
    constructor(): this(DSL.name("label_event"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, LabelEventRecord>): this(Internal.createPathAlias(child, key), child, key, LABEL_EVENT, null)
    override fun getSchema(): Schema = Public.PUBLIC
    override fun getReferences(): List<ForeignKey<LabelEventRecord, *>> = listOf(LABEL_EVENT__LABEL_EVENT_EVENT_ID, LABEL_EVENT__LABEL_EVENT_CATEGORY_ID)

    private lateinit var _event: Event
    private lateinit var _category: Category
    fun event(): Event {
        if (!this::_event.isInitialized)
            _event = Event(this, LABEL_EVENT__LABEL_EVENT_EVENT_ID)

        return _event;
    }
    fun category(): Category {
        if (!this::_category.isInitialized)
            _category = Category(this, LABEL_EVENT__LABEL_EVENT_CATEGORY_ID)

        return _category;
    }
    override fun `as`(alias: String): LabelEvent = LabelEvent(DSL.name(alias), this)
    override fun `as`(alias: Name): LabelEvent = LabelEvent(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): LabelEvent = LabelEvent(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): LabelEvent = LabelEvent(name, null)

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row2<String?, String?> = super.fieldsRow() as Row2<String?, String?>
}
