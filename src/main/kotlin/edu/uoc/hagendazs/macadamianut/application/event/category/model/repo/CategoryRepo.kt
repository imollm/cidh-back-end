package edu.uoc.hagendazs.macadamianut.application.event.category.model.repo

import edu.uoc.hagendazs.macadamianut.application.event.category.model.dataClass.Category

interface CategoryRepo {
    fun addCategory(category: Category): Category?
    fun updateCategory(category: Category): Category?
    fun showCategory(categoryId: String): Category?
    fun listAllCategories(): Collection<Category>
    fun existsByName(categoryName: String): Boolean
    fun findByName(categoryName: String?): Category?
    fun findById(categoryId: String): Category?
}