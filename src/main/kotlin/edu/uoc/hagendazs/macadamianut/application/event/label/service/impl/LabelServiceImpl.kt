package edu.uoc.hagendazs.macadamianut.application.event.label.service.impl

import edu.uoc.hagendazs.macadamianut.application.event.label.entrypoint.input.UpdateLabelRequest
import edu.uoc.hagendazs.macadamianut.common.HTTPMessages
import edu.uoc.hagendazs.macadamianut.application.event.label.model.dataClass.Label
import edu.uoc.hagendazs.macadamianut.application.event.label.model.repo.LabelRepo
import edu.uoc.hagendazs.macadamianut.application.event.label.service.LabelService
import edu.uoc.hagendazs.macadamianut.application.event.label.service.exceptions.LabelAlreadyExistsException
import edu.uoc.hagendazs.macadamianut.application.event.label.service.exceptions.LabelNotFoundException
import edu.uoc.hagendazs.macadamianut.application.event.label.service.exceptions.UnableToCreateLabel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LabelServiceImpl: LabelService {

    @Autowired
    lateinit var labelRepo: LabelRepo

    override fun addLabel(label: Label): Label? {
        if (labelRepo.existsByName(label.name)) {
            throw LabelAlreadyExistsException(HTTPMessages.ALREADY_EXISTS)
        }
        return labelRepo.addLabel(label) ?: run {
            throw UnableToCreateLabel(HTTPMessages.UNABLE_TO_CREATE)
        }
    }

    override fun updateLabel(labelId: String, updateLabelRequest: UpdateLabelRequest): Label? {
        val labelBody = updateLabelRequest.receive()
        val labelToUpdate = labelRepo.findById(labelId) ?: run {
            throw LabelNotFoundException(HTTPMessages.NOT_FOUND)
        }
        return labelRepo.updateLabel(this.copyLabelEntity(labelToUpdate, labelBody))
    }

    override fun findById(labelId: String): Label? {
        return labelRepo.showLabel(labelId)
    }

    override fun listAllLabels(): Collection<Label> {
        return labelRepo.listAllCategories()
    }

    override fun removeLabelById(labelId: String): Boolean? {
        return labelRepo.removeLabelById(labelId)
    }

    private fun copyLabelEntity(labelToUpdate: Label, incomingLabel: Label): Label {
        return labelToUpdate.copy(
            id = labelToUpdate.id,
            name = incomingLabel.name,
            description = incomingLabel.description
        )
    }

}
