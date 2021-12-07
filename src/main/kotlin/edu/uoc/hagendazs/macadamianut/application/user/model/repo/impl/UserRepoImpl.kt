package edu.uoc.hagendazs.macadamianut.application.user.model.repo.impl

import com.fasterxml.jackson.databind.ObjectMapper
import edu.uoc.hagendazs.generated.jooq.tables.references.ROLE
import edu.uoc.hagendazs.generated.jooq.tables.references.USER
import edu.uoc.hagendazs.generated.jooq.tables.references.USER_ROLE
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.MNUser
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.RoleEnum
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.UserRole
import edu.uoc.hagendazs.macadamianut.application.user.model.repo.UserRepo
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Repository
class UserRepoImpl : UserRepo {

    @Autowired
    protected lateinit var dsl: DSLContext

    @Autowired
    lateinit var objectMapper: ObjectMapper

    override fun findById(userId: String?): MNUser? {
        return dsl.selectFrom(USER)
            .where(USER.ID.eq(userId))
            .fetchSingle()
            .into(MNUser::class.java)
    }

    override fun findUserByEmail(email: String): MNUser? {
        return dsl.selectFrom(USER)
            .where(USER.EMAIL.eq(email))
            .fetchSingle()
            .into(MNUser::class.java)
    }

    override fun findAll(): Collection<MNUser> {
        return dsl.selectFrom(USER)
            .fetchInto(MNUser::class.java)
    }

    override fun updateUser(user: MNUser?): MNUser? {

        user?.id ?: return null

        dsl.update(USER)
            .set(USER.FIRST_NAME, user.firstName)
            .set(USER.LAST_NAME, user.lastName)
            .set(USER.NIF, user.nif)
            .set(USER.ADDRESS, user.address)
            .set(USER.EMAIL, user.email)
            .set(USER.IS_VALID_EMAIL, user.isValidEmail)
            .set(USER.PREFERRED_LANGUAGE, user.preferredLanguage)
            .execute()

        return this.findUserByEmail(user.id)

    }

    override fun changePassword(user: MNUser, newPassword: String) {
        dsl.update(USER)
            .set(USER.PASSWORD, newPassword)
            .execute()
    }

    override fun deleteUser(userId: String) {
        dsl.update(USER)
            .set(USER.DELETED_AT, LocalDateTime.now())
            .execute()
    }

    override fun existsByEmail(email: String): Boolean {
        return dsl.fetchExists(
            dsl.selectFrom(USER)
                .where(USER.EMAIL.eq(email))
        )
    }

    @Transactional
    override fun createUser(
        newUser: MNUser,
        role: RoleEnum,
    ): MNUser? {
        val userRecord = dsl.newRecord(USER, newUser)
        userRecord.store()

        dsl.insertInto(USER_ROLE)
            .set(USER_ROLE.ID, UUID.randomUUID().toString())
            .set(USER_ROLE.USER, userRecord.id)
            .set(USER_ROLE.ROLE, role.toString())


        return this.findById(newUser.id)
    }

    override fun permissionsForRole(role: RoleEnum): String? {
        val stringPermission =  dsl.select(ROLE.ROLE_DEFINITION_JSON)
            .from(ROLE)
            .where(ROLE.ID.eq(role.toString().uppercase()))
            .fetchOne()?.into(String::class.java)

        return objectMapper.readTree(stringPermission).toString()

    }

    override fun userRolesForUserId(userId: String): Iterable<UserRole> {
        return dsl.selectFrom(USER_ROLE)
            .where(USER_ROLE.USER.eq(userId))
            .fetchInto(UserRole::class.java)
    }
}