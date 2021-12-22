package edu.uoc.hagendazs.macadamianut.application.event.label.entrypoint.input

import edu.uoc.hagendazs.macadamianut.common.HTTPMessages
import edu.uoc.hagendazs.macadamianut.application.event.label.service.exceptions.LabelMissingValues

data class DeleteLabelRequest(
    val id: String
) {
    fun receive(): String {
        if (id.isBlank()) {
            throw LabelMissingValues(HTTPMessages.MISSING_VALUES)
        }
        return id
    }
}