package edu.uoc.hagendazs.macadamianut.application.event.label.entrypoint.input

import edu.uoc.hagendazs.macadamianut.common.HTTPMessages
import edu.uoc.hagendazs.macadamianut.application.event.label.model.dataClass.Label
import edu.uoc.hagendazs.macadamianut.application.event.label.service.exceptions.LabelMissingValues

data class CreateLabelRequest(
    val name: String,
    val description: String
) {
    fun receive(): Label {
        if (name.isBlank() || description.isBlank()) {
            throw LabelMissingValues(HTTPMessages.MISSING_VALUES)
        }
        return Label(
            name = name,
            description = description
        )
    }
}
