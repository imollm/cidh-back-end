package edu.uoc.hagendazs.macadamianut.application.event.event.model.repo.impl

import com.fasterxml.jackson.databind.ObjectMapper
import edu.uoc.hagendazs.macadamianut.application.event.event.model.dataclasses.CIDHEvent
import edu.uoc.hagendazs.macadamianut.application.event.event.model.repo.EventRepo
import mu.KotlinLogging
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class EventRepoImpl: EventRepo {


    @Autowired
    protected lateinit var dsl: DSLContext

    @Autowired
    lateinit var objectMapper: ObjectMapper

    private val logger = KotlinLogging.logger {}

    override fun findByName(name: String): CIDHEvent? {
        TODO("Not yet implemented")
    }

    override fun create(newEvent: CIDHEvent): CIDHEvent? {
        TODO("Not yet implemented")
    }

    override fun findById(id: String): CIDHEvent? {
        TODO("Not yet implemented")
    }

    override fun update(eventToUpdate: CIDHEvent): CIDHEvent? {
        TODO("Not yet implemented")
    }

    override fun eventsWithFilters(
        labels: Collection<String>,
        categories: Collection<String>,
        names: Collection<String>
    ): Collection<CIDHEvent> {
        TODO("Not yet implemented")
    }
}