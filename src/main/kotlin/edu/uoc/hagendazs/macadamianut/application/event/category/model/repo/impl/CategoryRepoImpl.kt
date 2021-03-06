package edu.uoc.hagendazs.macadamianut.application.event.category.model.repo.impl

import edu.uoc.hagendazs.macadamianut.application.event.category.model.dataClass.Category
import org.springframework.stereotype.Repository
import edu.uoc.hagendazs.macadamianut.application.event.category.model.repo.CategoryRepo
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import edu.uoc.hagendazs.generated.jooq.tables.references.CATEGORY
import org.springframework.transaction.annotation.Transactional

@Repository
class CategoryRepoImpl : CategoryRepo {

    @Autowired
    protected lateinit var dsl: DSLContext

    @Transactional
    override fun addCategory(category: Category): Category? {
        val categoryRecord = dsl.newRecord(CATEGORY, category)
        categoryRecord.store()

        return this.findByName(category.name)
    }

    override fun updateCategory(category: Category): Category? {
        dsl.update(CATEGORY)
            .set(CATEGORY.NAME, category.name)
            .set(CATEGORY.DESCRIPTION, category.description)
            .where(CATEGORY.ID.eq(category.id))
            .execute()

        return this.findById(category.id)
    }

    override fun showCategory(categoryId: String): Category? {
        return this.findById(categoryId)
    }

    override fun listAllCategories(): Collection<Category> {
        return dsl.selectFrom(CATEGORY).fetchInto(Category::class.java)
    }

    override fun existsByName(categoryName: String): Boolean {
        return dsl.fetchExists(
            dsl.selectFrom(CATEGORY)
                .where(CATEGORY.NAME.eq(categoryName))
        )
    }

    override fun findByName(categoryName: String?): Category? {
        categoryName ?: return null
        return dsl.selectFrom(CATEGORY)
            .where(CATEGORY.NAME.eq(categoryName))
            .fetchOne()
            ?.into(Category::class.java)
    }

    override fun findById(categoryId: String?): Category? {
        categoryId ?: return null
        return dsl.selectFrom(CATEGORY)
            .where(CATEGORY.ID.eq(categoryId))
            .fetchOne()
            ?.into(Category::class.java)
    }
}