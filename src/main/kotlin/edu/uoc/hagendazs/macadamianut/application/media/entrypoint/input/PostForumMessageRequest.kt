package edu.uoc.hagendazs.macadamianut.application.media.entrypoint.input

data class PostForumMessageRequest(
    val message: String,
    val parentMessageId: String?,
)
