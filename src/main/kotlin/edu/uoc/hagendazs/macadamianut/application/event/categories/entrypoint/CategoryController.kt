package edu.uoc.hagendazs.macadamianut.application.event.categories.entrypoint

import edu.uoc.hagendazs.macadamianut.application.event.categories.entrypoint.input.CreateCategoryRequest
import edu.uoc.hagendazs.macadamianut.application.event.categories.model.dataClass.Category
import edu.uoc.hagendazs.macadamianut.application.event.categories.service.CategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.server.ResponseStatusException
import java.net.URI
import javax.servlet.http.HttpServletRequest

@Controller
class CategoryController {

    @Autowired
    lateinit var categoryService: CategoryService

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PostMapping(value = ["categories"])
    fun createCategory(
        @RequestBody newCategoryReq: CreateCategoryRequest,
        request: HttpServletRequest
    ): ResponseEntity<Category> {

        val incomingCategory = newCategoryReq.recieve()
        val category = categoryService.addCategory(incomingCategory)
        category ?: run {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Unable to create category with given payload: $newCategoryReq"
            )
        }
        val categoryUriString = request.requestURL.toString().plus("/${category.id}")
        val categoryUri = URI.create(categoryUriString)
        return ResponseEntity.created(categoryUri).body(category)
    }

}