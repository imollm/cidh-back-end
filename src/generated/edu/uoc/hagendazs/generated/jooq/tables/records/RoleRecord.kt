/*
 * This file is generated by jOOQ.
 */
package edu.uoc.hagendazs.generated.jooq.tables.records


import edu.uoc.hagendazs.generated.jooq.tables.Role

import org.jooq.Field
import org.jooq.JSON
import org.jooq.Record1
import org.jooq.Record3
import org.jooq.Row3
import org.jooq.impl.UpdatableRecordImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class RoleRecord() : UpdatableRecordImpl<RoleRecord>(Role.ROLE), Record3<String?, String?, JSON?> {

    var id: String?
        set(value) = set(0, value)
        get() = get(0) as String?

    var roleName: String?
        set(value) = set(1, value)
        get() = get(1) as String?

    var roleDefinitionJson: JSON?
        set(value) = set(2, value)
        get() = get(2) as JSON?

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<String?> = super.key() as Record1<String?>

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    override fun fieldsRow(): Row3<String?, String?, JSON?> = super.fieldsRow() as Row3<String?, String?, JSON?>
    override fun valuesRow(): Row3<String?, String?, JSON?> = super.valuesRow() as Row3<String?, String?, JSON?>
    override fun field1(): Field<String?> = Role.ROLE.ID
    override fun field2(): Field<String?> = Role.ROLE.ROLE_NAME
    override fun field3(): Field<JSON?> = Role.ROLE.ROLE_DEFINITION_JSON
    override fun component1(): String? = id
    override fun component2(): String? = roleName
    override fun component3(): JSON? = roleDefinitionJson
    override fun value1(): String? = id
    override fun value2(): String? = roleName
    override fun value3(): JSON? = roleDefinitionJson

    override fun value1(value: String?): RoleRecord {
        this.id = value
        return this
    }

    override fun value2(value: String?): RoleRecord {
        this.roleName = value
        return this
    }

    override fun value3(value: JSON?): RoleRecord {
        this.roleDefinitionJson = value
        return this
    }

    override fun values(value1: String?, value2: String?, value3: JSON?): RoleRecord {
        this.value1(value1)
        this.value2(value2)
        this.value3(value3)
        return this
    }

    /**
     * Create a detached, initialised RoleRecord
     */
    constructor(id: String? = null, roleName: String? = null, roleDefinitionJson: JSON? = null): this() {
        this.id = id
        this.roleName = roleName
        this.roleDefinitionJson = roleDefinitionJson
    }
}
