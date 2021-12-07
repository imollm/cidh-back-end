package edu.uoc.hagendazs.macadamianut.administration.categories.service

import edu.uoc.hagendazs.macadamianut.administration.categories.model.dataClass.Category

interface CategoryService {
    fun addCategory(name: String, description: String): Category?
    fun updateCategory(id: String, name: String, description: String): Category?
    fun showCategory(id: String): Category?
    fun listAllCategories(): Collection<Category>
}