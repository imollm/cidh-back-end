package edu.uoc.hagendazs.macadamianut.application.event.category.entrypoint.output

import edu.uoc.hagendazs.macadamianut.application.event.category.model.dataClass.Category

data class UpdateCategoryResponse(
    val category: Category?
) { }