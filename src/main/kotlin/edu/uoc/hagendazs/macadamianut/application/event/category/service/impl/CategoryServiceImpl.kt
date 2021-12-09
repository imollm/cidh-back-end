package edu.uoc.hagendazs.macadamianut.application.event.category.service.impl

import edu.uoc.hagendazs.macadamianut.application.event.category.entrypoint.input.UpdateCategoryRequest
import edu.uoc.hagendazs.macadamianut.common.HTTPMessages
import edu.uoc.hagendazs.macadamianut.application.event.category.model.dataClass.Category
import edu.uoc.hagendazs.macadamianut.application.event.category.model.repo.CategoryRepo
import edu.uoc.hagendazs.macadamianut.application.event.category.service.CategoryService
import edu.uoc.hagendazs.macadamianut.application.event.category.service.exceptions.CategoryAlreadyExistsException
import edu.uoc.hagendazs.macadamianut.application.event.category.service.exceptions.CategoryNotFoundException
import edu.uoc.hagendazs.macadamianut.application.event.category.service.exceptions.UnableToCreateCategory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl: CategoryService {

    @Autowired
    lateinit var categoryRepo: CategoryRepo

    override fun addCategory(category: Category): Category? {
        if (categoryRepo.existsByName(category.name)) {
            throw CategoryAlreadyExistsException(HTTPMessages.ALREADY_EXISTS)
        }
        return categoryRepo.addCategory(category) ?: run {
            throw UnableToCreateCategory("Unable to create category $category")
        }
    }

    override fun updateCategory(categoryId: String, updateCategoryRequest: UpdateCategoryRequest): Category? {
        val categoryBody = updateCategoryRequest.receive()
        val categoryToUpdate = categoryRepo.findById(categoryId) ?: run {
            throw CategoryNotFoundException(HTTPMessages.NOT_FOUND)
        }
        return categoryRepo.updateCategory(this.copyCategoryEntity(categoryToUpdate, categoryBody))
    }

    override fun showCategory(categoryId: String): Category? {
        return categoryRepo.showCategory(categoryId)
    }

    override fun listAllCategories(): Collection<Category> {
        return categoryRepo.listAllCategories()
    }

    private fun copyCategoryEntity(categoryToUpdate: Category, incomingCategory: Category): Category {
        return categoryToUpdate.copy(
            name = incomingCategory.name,
            description = incomingCategory.description
        )
    }

}