package edu.uoc.hagendazs.macadamianut.application.event.categories.service

import edu.uoc.hagendazs.macadamianut.application.event.categories.model.dataClass.Category

interface CategoryService {
    fun addCategory(category: Category): Category?
    fun updateCategory(id: String, name: String, description: String): Category?
    fun showCategory(id: String): Category?
    fun listAllCategories(): Collection<Category>
}