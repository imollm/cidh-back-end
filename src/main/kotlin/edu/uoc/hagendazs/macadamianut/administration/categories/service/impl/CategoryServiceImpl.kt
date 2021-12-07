package edu.uoc.hagendazs.macadamianut.administration.categories.service.impl

import edu.uoc.hagendazs.macadamianut.administration.categories.model.dataClass.Category
import edu.uoc.hagendazs.macadamianut.administration.categories.service.CategoryService
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl: CategoryService {

    override fun addCategory(name: String, description: String): Category? {
        TODO("Not yet implemented")
    }

    override fun updateCategory(id: String, name: String, description: String): Category? {
        TODO("Not yet implemented")
    }

    override fun showCategory(id: String): Category? {
        TODO("Not yet implemented")
    }

    override fun listAllCategories(): Collection<Category> {
        TODO("Not yet implemented")
    }

}