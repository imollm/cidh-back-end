package edu.uoc.hagendazs.macadamianut.application.event.category.model.repo

import edu.uoc.hagendazs.macadamianut.application.event.category.model.dataClass.Category

interface CategoryRepo {
    fun addCategory(category: Category): Category?
    fun updateCategory(name: String, description: String): Category?
    fun showCategory(id: String): Category?
    fun listAllCategories(): Collection<Category>
    fun existsByName(name: String): Boolean
    fun findById(id: String): Category?
}