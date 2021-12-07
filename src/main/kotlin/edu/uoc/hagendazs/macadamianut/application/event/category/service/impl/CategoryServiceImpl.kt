package edu.uoc.hagendazs.macadamianut.application.event.category.service.impl

import edu.uoc.hagendazs.macadamianut.application.event.category.entrypoint.message.HTTPMessages
import edu.uoc.hagendazs.macadamianut.application.event.category.model.dataClass.Category
import edu.uoc.hagendazs.macadamianut.application.event.category.model.repo.CategoryRepo
import edu.uoc.hagendazs.macadamianut.application.event.category.service.CategoryService
import edu.uoc.hagendazs.macadamianut.application.event.category.service.exceptions.CategoryAlreadyExistsException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl: CategoryService {

    @Autowired
    lateinit var categoryRepo: CategoryRepo

    override fun addCategory(category: Category): Category? {
        if (categoryRepo.existsByName(category.name)) {
            throw CategoryAlreadyExistsException(HTTPMessages.ALREADY_EXISTS.message)
        }
        return categoryRepo.addCategory(category)
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