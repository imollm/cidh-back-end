package edu.uoc.hagendazs.macadamianut.application.event.category.entrypoint.input

data class UpdateCategoryRequest(
    val id: String?,
    val name: String?,
    val description: String?
) { }