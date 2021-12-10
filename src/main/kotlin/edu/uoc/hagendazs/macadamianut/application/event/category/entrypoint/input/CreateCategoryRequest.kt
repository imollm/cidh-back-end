package edu.uoc.hagendazs.macadamianut.application.event.category.entrypoint.input

import edu.uoc.hagendazs.macadamianut.common.HTTPMessages
import edu.uoc.hagendazs.macadamianut.application.event.category.model.dataClass.Category
import edu.uoc.hagendazs.macadamianut.application.event.category.service.exceptions.CategoryMissingValues

data class CreateCategoryRequest(
    val name: String,
    val description: String
) {
    fun receive(): Category {
        if (name.isBlank() || description.isBlank()) {
            throw CategoryMissingValues(HTTPMessages.MISSING_VALUES)
        }
        return Category(
            name = name,
            description = description
        )
    }
}
