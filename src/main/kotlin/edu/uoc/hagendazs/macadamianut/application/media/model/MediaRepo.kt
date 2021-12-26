package edu.uoc.hagendazs.macadamianut.application.media.model

interface MediaRepo {
    fun ratingForEvent(eventId: String): Number
}
