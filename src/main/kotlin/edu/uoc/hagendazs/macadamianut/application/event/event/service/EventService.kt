package edu.uoc.hagendazs.macadamianut.application.event.event.service

import edu.uoc.hagendazs.macadamianut.application.event.event.model.dataclasses.CIDHEvent

interface EventService {
    fun createEvent(newEvent: CIDHEvent): CIDHEvent?

}
