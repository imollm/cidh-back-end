package edu.uoc.hagendazs.macadamianut.application.event.category.entrypoint.input

import edu.uoc.hagendazs.macadamianut.application.event.category.entrypoint.message.HTTPMessages
import edu.uoc.hagendazs.macadamianut.application.event.category.model.dataClass.Category
import edu.uoc.hagendazs.macadamianut.application.event.category.service.exceptions.CategoryMissingValues

data class CreateCategoryRequest(
    val name: String,
    val description: String
) {
    fun recieve(): Category {
        if (name.isBlank() || description.isBlank()) {
            throw CategoryMissingValues(HTTPMessages.MISSING_VALUES.message)
        }
        return Category(
            name = name,
            description = description
        )
    }
}
