package edu.uoc.hagendazs.macadamianut.administration.categories.model.repo.impl

import edu.uoc.hagendazs.macadamianut.administration.categories.model.dataClass.Category
import org.springframework.stereotype.Repository
import edu.uoc.hagendazs.macadamianut.administration.categories.model.repo.CategoryRepo

@Repository
class CategoryRepoImpl: CategoryRepo {

    override fun addCategory(name: String, description: String): Category? {
        TODO("Not yet implemented")
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

}