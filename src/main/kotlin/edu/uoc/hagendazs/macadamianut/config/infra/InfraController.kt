package edu.uoc.hagendazs.macadamianut.config.infra

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.info.BuildProperties
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class InfraController {

    @Autowired
    private lateinit var buildProperties: BuildProperties

    @GetMapping(value = ["/ping"])
    fun healthCheck(): ResponseEntity<Void> {
        return ResponseEntity.ok().build()
    }

    @GetMapping("/build-info")
    fun buildInfo(): ResponseEntity<BuildInfoResponse> {
        return ResponseEntity.ok(
            BuildInfoResponse(
                version = buildProperties.version
            )
        )
    }

}