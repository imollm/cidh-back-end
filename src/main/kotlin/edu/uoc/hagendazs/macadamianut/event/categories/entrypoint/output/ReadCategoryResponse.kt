package edu.uoc.hagendazs.macadamianut.event.categories.entrypoint.output

import edu.uoc.hagendazs.macadamianut.event.categories.model.dataClass.Category
import edu.uoc.hagendazs.macadamianut.common.entrypoint.output.CommonResponse

data class ReadCategoryResponse(
    val response: CommonResponse,
    val category: Category?
)