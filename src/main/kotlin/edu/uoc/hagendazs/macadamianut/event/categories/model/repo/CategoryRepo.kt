package edu.uoc.hagendazs.macadamianut.event.categories.model.repo

import edu.uoc.hagendazs.macadamianut.event.categories.model.dataClass.Category

interface CategoryRepo {
    fun addCategory(category: Category): Category?
    fun updateCategory(name: String, description: String): Category?
    fun showCategory(id: String): Category?
    fun listAllCategories(): Collection<Category>
    fun existsByName(name: String): Boolean
    fun findById(id: String): Category
}