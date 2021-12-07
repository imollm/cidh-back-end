package edu.uoc.hagendazs.macadamianut.application.event.categories.entrypoint.output

import edu.uoc.hagendazs.macadamianut.application.event.categories.model.dataClass.Category
import edu.uoc.hagendazs.macadamianut.common.entrypoint.output.CommonResponse

data class UpdateCategoryResponse(
    val response: CommonResponse,
    val category: Category?
) { }