package edu.uoc.hagendazs.macadamianut.application.event.category.entrypoint.input

import edu.uoc.hagendazs.macadamianut.common.HTTPMessages
import edu.uoc.hagendazs.macadamianut.application.event.category.model.dataClass.Category
import edu.uoc.hagendazs.macadamianut.application.event.category.service.exceptions.CategoryMissingValues

data class CreateCategoryRequest(
    val name: String,
    val description: String
) {
    init {
        require(name.isNotBlank()) {
            "Name could not be bull or empty"
        }
        val doesNotHaveWhiteSpaces = name.replace(regex = Regex("\\s+"),  replacement = "").length == name.length
        require(doesNotHaveWhiteSpaces) {
            "Category names could not contain white spaces"
        }
        require(description.isNotBlank()) {
            "Description could not be null or empty"
        }
    }

    fun receive(): Category {
        return Category(
            name = name,
            description = description
        )
    }
}
