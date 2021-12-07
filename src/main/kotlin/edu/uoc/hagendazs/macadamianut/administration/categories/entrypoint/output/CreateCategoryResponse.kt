package edu.uoc.hagendazs.macadamianut.administration.categories.entrypoint.output

import edu.uoc.hagendazs.macadamianut.administration.categories.model.dataClass.Category
import edu.uoc.hagendazs.macadamianut.common.entrypoint.output.CommonResponse

data class CreateCategoryResponse(
    val response: CommonResponse,
    val category: Category?
)