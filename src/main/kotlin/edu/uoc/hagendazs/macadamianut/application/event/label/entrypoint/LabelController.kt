package edu.uoc.hagendazs.macadamianut.application.event.label.entrypoint

import edu.uoc.hagendazs.macadamianut.application.event.label.entrypoint.input.CreateLabelRequest
import edu.uoc.hagendazs.macadamianut.application.event.label.entrypoint.input.UpdateLabelRequest
import edu.uoc.hagendazs.macadamianut.common.HTTPMessages
import edu.uoc.hagendazs.macadamianut.application.event.label.model.dataClass.Label
import edu.uoc.hagendazs.macadamianut.application.event.label.service.LabelService
import edu.uoc.hagendazs.macadamianut.application.event.label.service.exceptions.LabelNotFoundException
import edu.uoc.hagendazs.macadamianut.application.event.label.service.exceptions.UnableToDeleteLabel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.net.URI
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/v1/labels")
class LabelController {

    @Autowired
    lateinit var labelService: LabelService

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PostMapping(value = [""])
    fun createLabel(
        @RequestBody newLabelReq: CreateLabelRequest,
        request: HttpServletRequest
    ): ResponseEntity<Label> {

        val incomingLabel = newLabelReq.receive()
        val label = labelService.addLabel(incomingLabel)
        label ?: run {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                HTTPMessages.ERROR_SAVING_A_RESOURCE
            )
        }
        val labelUriString = request.requestURL.toString().plus("/${label.id}")
        val labelUri = URI.create(labelUriString)
        return ResponseEntity.created(labelUri).body(label)
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PostMapping(value = ["/{labelId}"])
    fun updateLabel(
        @PathVariable labelId: String,
        @RequestBody updateLabelReq: UpdateLabelRequest,
    ): ResponseEntity<Label> {
        val label = labelService.updateLabel(labelId, updateLabelReq)
        return ResponseEntity.ok(label)
    }

    @GetMapping(value = ["/{labelId}"])
    fun getLabelById(
        @PathVariable labelId: String
    ): ResponseEntity<Label> {
        val label = labelService.findById(labelId) ?: run {
            throw LabelNotFoundException(HTTPMessages.NOT_FOUND)
        }
        return ResponseEntity.ok(label)
    }

    @GetMapping(value = [""])
    fun getAllCategories(): ResponseEntity<Collection<Label>> {
        val categories = labelService.listAllLabels()
        return ResponseEntity.ok(categories)
    }

    @DeleteMapping(value = ["/{labelId}"])
    fun deleteLabelById(
        @PathVariable labelId: String
    ): ResponseEntity<Void> {
        labelService.removeLabelById(labelId) ?: run {
            throw UnableToDeleteLabel(HTTPMessages.UNABLE_TO_DELETE)
        }

        return ResponseEntity.ok().build()
    }
}
