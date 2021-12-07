package edu.uoc.hagendazs.macadamianut.application.event.categories.entrypoint.message

enum class HTTPMessages(val message: String) {
    MISSING_VALUES("Some fields doesn't have value"),
    ALREADY_EXISTS("This category already exists"),
    NOT_FOUND("This category not exists")
}