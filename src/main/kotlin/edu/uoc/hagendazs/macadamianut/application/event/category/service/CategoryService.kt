package edu.uoc.hagendazs.macadamianut.application.event.category.service

import edu.uoc.hagendazs.macadamianut.application.event.category.entrypoint.input.UpdateCategoryRequest
import edu.uoc.hagendazs.macadamianut.application.event.category.model.dataClass.Category

interface CategoryService {
    fun addCategory(category: Category): Category?
    fun updateCategory(categoryId: String, updateCategoryRequest: UpdateCategoryRequest): Category?
    fun showCategory(categoryId: String): Category?
    fun listAllCategories(): Collection<Category>
}