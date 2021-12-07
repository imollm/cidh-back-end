package edu.uoc.hagendazs.macadamianut.application.event.categories.model.repo.impl

import edu.uoc.hagendazs.macadamianut.application.event.categories.model.dataClass.Category
import org.springframework.stereotype.Repository
import edu.uoc.hagendazs.macadamianut.application.event.categories.model.repo.CategoryRepo
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import edu.uoc.hagendazs.generated.jooq.tables.references.CATEGORIES
import org.springframework.transaction.annotation.Transactional

@Repository
class CategoryRepoImpl: CategoryRepo {

    @Autowired
    protected lateinit var dsl: DSLContext

    @Transactional
    override fun addCategory(category: Category): Category? {
        val categoryRecord = dsl.newRecord(CATEGORIES, category)
        categoryRecord.store()

        return this.findById(category.id)
    }

    override fun updateCategory(name: String, description: String): Category? {
        TODO("Not yet implemented")
    }

    override fun showCategory(id: String): Category? {
        TODO("Not yet implemented")
    }

    override fun listAllCategories(): Collection<Category> {
        TODO("Not yet implemented")
    }

    override fun existsByName(name: String): Boolean? {
        return dsl.fetchExists(
            dsl.selectFrom(CATEGORIES)
                .where(CATEGORIES.NAME.eq(name))
        )
    }

    override fun findById(id: String): Category? {
        return dsl.selectFrom(CATEGORIES)
            .where(CATEGORIES.ID.eq(id))
            .fetchSingle()
            .into(Category::class.java)
    }

}