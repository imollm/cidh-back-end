package edu.uoc.hagendazs.macadamianut.application.event.label.model.repo

import edu.uoc.hagendazs.macadamianut.application.event.label.model.dataClass.Label

interface LabelRepo {
    fun addLabel(label: Label): Label?
    fun updateLabel(label: Label): Label?
    fun showLabel(labelId: String): Label?
    fun listAllCategories(): Collection<Label>
    fun existsByName(labelName: String): Boolean
    fun findByName(labelName: String): Label?
    fun findById(labelId: String): Label?
    fun removeLabelById(labelId: String): Boolean?
    fun labelsForEvent(eventId: String?): Collection<Label>
    fun findLabelsWithId(labelIds: Collection<String>): Collection<Label>
}
