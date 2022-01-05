package edu.uoc.hagendazs.macadamianut.config.infra

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class InfraController {

    @GetMapping(value = ["/ping"])
    fun healthCheck(): ResponseEntity<Void> {
        return ResponseEntity.ok().build()
    }

}