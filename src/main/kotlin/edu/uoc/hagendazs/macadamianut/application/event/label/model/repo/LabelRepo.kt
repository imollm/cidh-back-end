package edu.uoc.hagendazs.macadamianut.application.event.label.model.repo

import edu.uoc.hagendazs.macadamianut.application.event.label.model.dataclasses.EventLabel

interface LabelRepo {
    fun findByName(labels: Collection<String>): Collection<EventLabel>
}