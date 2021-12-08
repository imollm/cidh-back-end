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

        return this.findById(category.id)
    }

    override fun updateCategory(category: Category): Category? {
        dsl.update(CATEGORY)
            .set(CATEGORY.NAME, category.name)
            .set(CATEGORY.DESCRIPTION, category.description)
            .execute()

        return this.findById(category.id)
    }

    override fun showCategory(id: String): Category? {
        TODO("Not yet implemented")
    }

    override fun listAllCategories(): Collection<Category> {
        TODO("Not yet implemented")
    }

    override fun existsByName(name: String): Boolean {
        return dsl.fetchExists(
            dsl.selectFrom(CATEGORY)
                .where(CATEGORY.NAME.eq(name))
        )
    }

    override fun findById(id: String): Category? {
        return dsl.selectFrom(CATEGORY)
            .where(CATEGORY.ID.eq(id))
            .fetchSingle()
            .into(Category::class.java)
    }

    override fun existsById(id: String): Boolean {
        return dsl.fetchExists(
            dsl.selectFrom(CATEGORY)
                .where(CATEGORY.ID.eq(id))
        )
    }

}