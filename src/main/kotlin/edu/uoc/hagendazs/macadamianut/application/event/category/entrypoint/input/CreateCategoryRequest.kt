package edu.uoc.hagendazs.macadamianut.application.event.category.entrypoint.input

import edu.uoc.hagendazs.macadamianut.application.event.category.entrypoint.message.HTTPMessages
import edu.uoc.hagendazs.macadamianut.application.event.category.model.dataClass.Category
import edu.uoc.hagendazs.macadamianut.application.event.category.service.exceptions.CategoryMissingValues

data class CreateCategoryRequest(
    val name: String,
    val description: String
) {
    init {
        require(name.isNotBlank()) { "Name is blank" }
        require(description.isNotBlank()) { "Name is blank" }
    }

    fun receive(): Category {
        return Category(
            name = name,
            description = description
        )
    }
}
