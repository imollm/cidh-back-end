package edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.model.repo.impl

import edu.uoc.hagendazs.generated.jooq.tables.references.EVENT_ORGANIZER
import edu.uoc.hagendazs.macadamianut.application.event.category.model.dataClass.Category
import edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.model.dataClass.EventOrganizer
import edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.model.repo.EventOrganizerRepo
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class EventOrganizerRepoImpl: EventOrganizerRepo {

    @Autowired
    protected lateinit var dsl: DSLContext

    override fun addEventOrganizer(eventOrganizer: EventOrganizer): EventOrganizer? {
        val eventOrganizerRecord = dsl.newRecord(EVENT_ORGANIZER, eventOrganizer)
        eventOrganizerRecord.store()

        return this.findByName(eventOrganizer.name)
    }

    override fun updateEventOrganizer(eventOrganizer: EventOrganizer): EventOrganizer? {
        dsl.update(EVENT_ORGANIZER)
            .set(EVENT_ORGANIZER.NAME, eventOrganizer.name)
            .set(EVENT_ORGANIZER.DESCRIPTION, eventOrganizer.description)
            .set(EVENT_ORGANIZER.ADMIN, eventOrganizer.admin)
            .where(EVENT_ORGANIZER.ID.eq(eventOrganizer.id))
            .execute()

        return this.findById(eventOrganizer.id)
    }

    override fun getEventOrganizer(eventOrganizerId: String): EventOrganizer? {
        return this.findById(eventOrganizerId)
    }

    override fun listAllEventOrganizers(): Collection<EventOrganizer> {
        return dsl.selectFrom(EVENT_ORGANIZER).fetchInto(EventOrganizer::class.java)
    }

    override fun existsByName(eventOrganizerName: String): Boolean {
        return dsl.fetchExists(
            dsl.selectFrom(EVENT_ORGANIZER)
                .where(EVENT_ORGANIZER.NAME.eq(eventOrganizerName))
        )
    }

    override fun findByName(eventOrganizerName: String): EventOrganizer? {
        return dsl.selectFrom(EVENT_ORGANIZER)
            .where(EVENT_ORGANIZER.NAME.eq(eventOrganizerName))
            .fetchOne()
            ?.into(EventOrganizer::class.java)
    }

    override fun findById(eventOrganizerId: String): EventOrganizer? {
        return dsl.selectFrom(EVENT_ORGANIZER)
            .where(EVENT_ORGANIZER.ID.eq(eventOrganizerId))
            .fetchOne()
            ?.into(EventOrganizer::class.java)
    }
}
