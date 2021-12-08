package edu.uoc.hagendazs.macadamianut.application.event.category.entrypoint

import edu.uoc.hagendazs.macadamianut.application.event.category.entrypoint.input.CreateCategoryRequest
import edu.uoc.hagendazs.macadamianut.application.event.category.entrypoint.input.UpdateCategoryRequest
import edu.uoc.hagendazs.macadamianut.application.event.category.entrypoint.message.HTTPMessages
import edu.uoc.hagendazs.macadamianut.application.event.category.model.dataClass.Category
import edu.uoc.hagendazs.macadamianut.application.event.category.service.CategoryService
import edu.uoc.hagendazs.macadamianut.application.event.category.service.exceptions.CategoryNotFoundException
import org.jooq.exception.NoDataFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.net.URI
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/v1/categories")
class CategoryController {

    @Autowired
    lateinit var categoryService: CategoryService

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PostMapping(value = [""])
    fun createCategory(
        @RequestBody newCategoryReq: CreateCategoryRequest,
        request: HttpServletRequest
    ): ResponseEntity<Category> {

        val incomingCategory = newCategoryReq.recieve()
        val category = categoryService.addCategory(incomingCategory)
        category ?: run {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                HTTPMessages.ERROR_SAVING_A_RESOURCE.message
            )
        }
        val categoryUriString = request.requestURL.toString().plus("/${category.id}")
        val categoryUri = URI.create(categoryUriString)
        return ResponseEntity.created(categoryUri).body(category)
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PutMapping(value = ["/{categoryId}"])
    fun updateCategory(
        @PathVariable categoryId: String?,
        @RequestBody updateCategoryReq: UpdateCategoryRequest,
        request: HttpServletRequest,
    ): ResponseEntity<Category> {
        categoryId ?: run {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST,
            HTTPMessages.MISSING_VALUES.message
                )
        }
        val category = categoryService.updateCategory(categoryId, updateCategoryReq)
        return ResponseEntity.ok(category)
    }

    @GetMapping(value = ["/{categoryId}"])
    fun getCategoryById(
        @PathVariable categoryId: String
    ): ResponseEntity<Category> {
        val category = categoryService.showCategory(categoryId)
        return ResponseEntity.ok(category)
    }

}