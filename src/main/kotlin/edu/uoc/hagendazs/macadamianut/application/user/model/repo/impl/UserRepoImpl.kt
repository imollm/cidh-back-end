package edu.uoc.hagendazs.macadamianut.application.user.model.repo.impl

import edu.uoc.hagendazs.generated.jooq.tables.references.USER
import edu.uoc.hagendazs.generated.jooq.tables.references.USER_ROLE
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.MNUser
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.UserRole
import edu.uoc.hagendazs.macadamianut.application.user.model.repo.UserRepo
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class UserRepoImpl : UserRepo {

    @Autowired
    protected lateinit var dsl: DSLContext

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

    override fun createUser(newUser: MNUser): MNUser? {
        val userRecord = dsl.newRecord(USER, newUser)
        userRecord.store()
        return this.findById(newUser.id)
    }

    override fun userRolesForUserId(userId: String): Iterable<UserRole> {
        return dsl.selectFrom(USER_ROLE)
            .where(USER_ROLE.PERSON.eq(userId))
            .fetchInto(UserRole::class.java)
    }
}