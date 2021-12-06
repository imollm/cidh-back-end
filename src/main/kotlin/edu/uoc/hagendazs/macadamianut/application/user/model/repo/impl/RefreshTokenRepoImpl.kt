package edu.uoc.hagendazs.macadamianut.application.user.model.repo.impl

import edu.uoc.hagendazs.generated.jooq.tables.RefreshToken.Companion.REFRESH_TOKEN
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.RefreshToken
import edu.uoc.hagendazs.macadamianut.application.user.model.repo.RefreshTokenRepo
import org.jooq.Condition
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class RefreshTokenRepoImpl : RefreshTokenRepo {

    @Autowired
    protected lateinit var dsl: DSLContext

    private inline fun <reified TargetType> findByCondition(
        whereCondition: Condition
    ) = dsl.selectFrom(REFRESH_TOKEN)
        .where(whereCondition)
        .fetchOne()
        ?.into(TargetType::class.java)

    override fun findByToken(token: String): RefreshToken? {
        return findByCondition(REFRESH_TOKEN.ID.eq(token))
    }

    override fun findTokensForUserId(userId: String): Collection<RefreshToken> {
        return findByCondition(REFRESH_TOKEN.USER_ID.eq(userId)) ?: ArrayList()
    }

    override fun save(refreshToken: RefreshToken): RefreshToken? {
        dsl.newRecord(REFRESH_TOKEN, refreshToken).store()
        return findByCondition(REFRESH_TOKEN.ID.eq(refreshToken.id))
    }

    override fun deleteToken(refreshToken: String): Int {
        return dsl.deleteFrom(REFRESH_TOKEN)
            .where(REFRESH_TOKEN.ID.eq(refreshToken))
            .execute()
    }

    override fun deleteTokensForUser(userId: String): Int {
        return dsl.deleteFrom(REFRESH_TOKEN)
            .where(REFRESH_TOKEN.USER_ID.eq(userId))
            .execute()
    }
}