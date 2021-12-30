package edu.uoc.hagendazs.macadamianut.application.event.event.entrypoint.output

import edu.uoc.hagendazs.macadamianut.application.event.category.model.dataClass.Category
import edu.uoc.hagendazs.macadamianut.application.event.event.model.CIDHEvent
import edu.uoc.hagendazs.macadamianut.application.event.event.model.dataClass.DBEvent
import edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.model.dataClass.EventOrganizer
import edu.uoc.hagendazs.macadamianut.application.event.label.model.dataClass.Label
import edu.uoc.hagendazs.macadamianut.application.media.model.dataClass.EventRating
import java.net.URI
import java.time.LocalDateTime
import java.util.*

data class EventResponse(
    override val id: String = UUID.randomUUID().toString(),
    val name: String,
    val headerImage: URI?,
    val rating: EventRating,
    val userRating: Number?,
    val description: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val category: Category,
    val labels: Collection<Label>,
    val eventOrganizer: EventOrganizer,
    val eventUrl: String,
    val isFavorite: Boolean,
    val isUserSubscribed: Boolean,
) : CIDHEvent

fun DBEvent.toEventResponse(
    rating: EventRating,
    category: Category,
    labels: Collection<Label>,
    eventOrganizer: EventOrganizer,
    isFavorite: Boolean,
    isUserSubscribed: Boolean,
    userRating: Number?,
): EventResponse {
    return EventResponse(
        id = id,
        name = name,
        headerImage = headerImage,
        rating = rating,
        description = description,
        startDate = startDate,
        endDate = endDate,
        category = category,
        labels = labels,
        eventOrganizer = eventOrganizer,
        eventUrl = eventUrl,
        isFavorite = isFavorite,
        isUserSubscribed = isUserSubscribed,
        userRating = userRating,
    )
}
