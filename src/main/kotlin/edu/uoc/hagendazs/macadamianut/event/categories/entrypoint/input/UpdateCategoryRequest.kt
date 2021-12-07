package edu.uoc.hagendazs.macadamianut.event.categories.entrypoint.input

data class UpdateCategoryRequest(
    val id: String?,
    val name: String?,
    val description: String?
) { }