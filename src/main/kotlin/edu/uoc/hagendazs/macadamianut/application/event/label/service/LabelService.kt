package edu.uoc.hagendazs.macadamianut.application.event.label.service

import edu.uoc.hagendazs.macadamianut.application.event.label.entrypoint.input.UpdateLabelRequest
import edu.uoc.hagendazs.macadamianut.application.event.label.model.dataClass.Label

interface LabelService {
    fun addLabel(label: Label): Label?
    fun updateLabel(labelId: String, updateLabelRequest: UpdateLabelRequest): Label?
    fun showLabel(labelId: String): Label?
    fun listAllLabels(): Collection<Label>
    fun removeLabelById(labelId: String): Boolean?
}
