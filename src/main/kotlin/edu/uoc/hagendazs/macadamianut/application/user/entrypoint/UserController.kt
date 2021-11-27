package edu.uoc.hagendazs.macadamianut.application.user.entrypoint

import edu.uoc.hagendazs.macadamianut.application.user.entrypoint.input.UserPasswordReq
import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.MNUser
import edu.uoc.hagendazs.macadamianut.application.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class UserController {

    @Autowired
    lateinit var userService: UserService

    @PostMapping(value = ["users"])
    fun createUser(
        @RequestBody newUserReq: UserPasswordReq
    ){

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @RequestMapping(value = ["/v2/users"], method = [(RequestMethod.GET)])
    fun getAllUsers(): ResponseEntity<Collection<MNUser>> {
        val persons = userService.findAll()
        return ResponseEntity.ok(persons)
    }

}