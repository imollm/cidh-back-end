package edu.uoc.hagendazs.macadamianut.application.media.entrypoint.output

import edu.uoc.hagendazs.macadamianut.application.media.model.dataClass.UserEventComment
import java.time.LocalDateTime

data class EventCommentResponse(
    val authorId: String,
    val comment: String,
    val userFullName: String,
    val createdAt: LocalDateTime
)

fun UserEventComment.toEventCommentResponse(
    userFullName: String,
): EventCommentResponse {
    return EventCommentResponse(
        authorId = userId,
        comment = comment,
        createdAt = createdAt,
        userFullName = userFullName,
    )
}