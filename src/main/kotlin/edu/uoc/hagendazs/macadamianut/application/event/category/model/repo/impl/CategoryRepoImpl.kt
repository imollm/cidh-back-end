package edu.uoc.hagendazs.macadamianut.application.event.category.model.repo.impl

import edu.uoc.hagendazs.macadamianut.application.event.category.model.dataClass.Category
import org.springframework.stereotype.Repository
import edu.uoc.hagendazs.macadamianut.application.event.category.model.repo.CategoryRepo
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import edu.uoc.hagendazs.generated.jooq.tables.references.CATEGORY
import edu.uoc.hagendazs.macadamianut.application.event.category.entrypoint.message.HTTPMessages
import edu.uoc.hagendazs.macadamianut.application.event.category.service.exceptions.CategoryNotFoundException
import org.jooq.exception.NoDataFoundException
import org.springframework.transaction.annotation.Transactional

@Repository
class CategoryRepoImpl : CategoryRepo {

    @Autowired
    protected lateinit var dsl: DSLContext

    @Transactional
    override fun addCategory(category: Category): Category? {
        val categoryRecord = dsl.newRecord(CATEGORY, category)
        categoryRecord.store()

        return this.findById(category.id)
    }

    override fun updateCategory(category: Category): Category? {
        dsl.update(CATEGORY)
            .set(CATEGORY.NAME, category.name)
            .set(CATEGORY.DESCRIPTION, category.description)
            .execute()

        return this.findById(category.id)
    }

    override fun showCategory(categoryId: String): Category? {
        return this.findById(categoryId)
    }

    override fun listAllCategories(): Collection<Category> {
        return dsl.selectFrom(CATEGORY).fetchInto(Category::class.java)
    }

    override fun existsByName(name: String): Boolean {
        return dsl.fetchExists(
            dsl.selectFrom(CATEGORY)
                .where(CATEGORY.NAME.eq(name))
        )
    }

    override fun findById(id: String): Category? {
        try {
            return dsl.selectFrom(CATEGORY)
                .where(CATEGORY.ID.eq(id))
                .fetchSingle()
                .into(Category::class.java)
        } catch (e: NoDataFoundException) {
            throw CategoryNotFoundException(HTTPMessages.NOT_FOUND.message)
        }
    }

    override fun existsById(id: String): Boolean {
        return dsl.fetchExists(
            dsl.selectFrom(CATEGORY)
                .where(CATEGORY.ID.eq(id))
        )
    }

}