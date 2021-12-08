package edu.uoc.hagendazs.macadamianut.application.event.category.entrypoint.message

enum class HTTPMessages(val message: String) {
    CREATED("The resource was created successfully"),
    MISSING_VALUES("Some fields doesn't have value"),
    ALREADY_EXISTS("This resource already exists"),
    NOT_FOUND("This resource not exists"),
    ERROR_SAVING_A_RESOURCE("Error saving resource")
}