package edu.uoc.hagendazs.macadamianut.administration.categories.model.repo

import edu.uoc.hagendazs.macadamianut.administration.categories.model.dataClass.Category

interface CategoryRepo {
    fun addCategory(name: String, description: String): Category?
    fun updateCategory(name: String, description: String): Category?
    fun showCategory(id: String): Category?
    fun listAllCategories(): Collection<Category>
}