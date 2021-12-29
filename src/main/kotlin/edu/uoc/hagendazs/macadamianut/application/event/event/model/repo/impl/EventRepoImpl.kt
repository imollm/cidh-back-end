package edu.uoc.hagendazs.macadamianut.application.event.event.model.repo.impl

import com.fasterxml.jackson.databind.ObjectMapper
import edu.uoc.hagendazs.generated.jooq.tables.references.*
import edu.uoc.hagendazs.macadamianut.application.event.category.model.repo.CategoryRepo
import edu.uoc.hagendazs.macadamianut.application.event.event.entrypoint.output.EventResponse
import edu.uoc.hagendazs.macadamianut.application.event.event.entrypoint.output.toEventResponse
import edu.uoc.hagendazs.macadamianut.application.event.event.model.dataClass.DBEvent
import edu.uoc.hagendazs.macadamianut.application.event.event.model.repo.EventRepo
import edu.uoc.hagendazs.macadamianut.application.event.eventOrganizer.model.repo.EventOrganizerRepo
import edu.uoc.hagendazs.macadamianut.application.event.label.model.repo.LabelRepo
import edu.uoc.hagendazs.macadamianut.application.media.model.MediaRepo
import mu.KotlinLogging
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.impl.DSL.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class EventRepoImpl : EventRepo {

    @Autowired
    protected lateinit var labelRepo: LabelRepo

    @Autowired
    protected lateinit var categoryRepo: CategoryRepo

    @Autowired
    protected lateinit var eventOrganizerRepo: EventOrganizerRepo

    @Autowired
    protected lateinit var mediaRepo: MediaRepo

    @Autowired
    protected lateinit var dsl: DSLContext

    @Autowired
    lateinit var objectMapper: ObjectMapper

    private val logger = KotlinLogging.logger {}

    override fun findById(id: String): EventResponse? {
        val dbEvent = dsl.selectFrom(EVENT)
            .where(EVENT.ID.eq(id))
            .fetchOne()
            ?.into(DBEvent::class.java)

        return toEventObject(dbEvent)
    }

    override fun findByName(name: String): EventResponse? {
        val dbEvent = dsl.selectFrom(EVENT)
            .where(EVENT.NAME.eq(name))
            .fetchOne()
            ?.into(DBEvent::class.java)

        return toEventObject(dbEvent)
    }

    private fun toEventObject(dbEvent: DBEvent?): EventResponse? {
        dbEvent ?: return null
        val category = categoryRepo.findById(dbEvent.categoryId) ?: kotlin.run {
            throw IllegalStateException("Category cannot be null")
        }
        val labels = labelRepo.labelsForEvent(dbEvent.id)
        val eventOrganizer = eventOrganizerRepo.getEventOrganizer(dbEvent.organizerId) ?: kotlin.run {
            throw IllegalStateException("Event Organizer cannot be null")
        }
        val rating = mediaRepo.ratingForEvent(dbEvent.id) ?: kotlin.run {
            throw IllegalStateException("Rating cannot be null")
        }

        return dbEvent.toEventResponse(
            rating = rating,
            category = category,
            labels = labels,
            eventOrganizer = eventOrganizer
        )
    }

    override fun create(newEvent: DBEvent, labelIds: Collection<String>): EventResponse? {
        val eventRecord = dsl.newRecord(EVENT, newEvent)
        eventRecord.store()
        insertLabelsForEvent(labelIds, newEvent)
        return this.findById(newEvent.id)
    }

    @Transactional
    override fun update(eventToUpdate: DBEvent, labelIds: Collection<String>): EventResponse? {
        dsl.update(EVENT)
            .set(EVENT.NAME, eventToUpdate.name)
            .set(EVENT.DESCRIPTION, eventToUpdate.description)
            .set(EVENT.HEADER_IMAGE, eventToUpdate.headerImage.toString())
            .set(EVENT.START_DATE, eventToUpdate.startDate)
            .set(EVENT.END_DATE, eventToUpdate.endDate)
            .set(EVENT.CATEGORY_ID, eventToUpdate.categoryId)
            .execute()

        val existingLabelIds = dsl.select(LABEL_EVENT.LABEL_ID)
            .from(LABEL_EVENT)
            .where(LABEL_EVENT.LABEL_ID.`in`(labelIds))
            .fetchInto(String::class.java)
            .toSet()

        val labelIdsToInsert = labelIds.minus(existingLabelIds)
        insertLabelsForEvent(labelIdsToInsert, eventToUpdate)

        val labelsToDelete = existingLabelIds.minus(labelIds.toSet())
        deleteLabelsForEvent(labelsToDelete, eventToUpdate)

        return findById(eventToUpdate.id)
    }

    private fun deleteLabelsForEvent(labelsToDelete: Set<String>, eventToUpdate: DBEvent) {
        dsl.deleteFrom(LABEL_EVENT)
            .where(LABEL_EVENT.EVENT_ID.eq(eventToUpdate.id))
            .and(LABEL_EVENT.LABEL_ID.`in`(labelsToDelete))
            .execute()
    }

    private fun insertLabelsForEvent(
        labelIdsToInsert: Collection<String>,
        eventToUpdate: DBEvent
    ) {
        // jOOQ requires dummy bind values for the original query
        // https://www.jooq.org/doc/3.15/manual/sql-execution/batch-execution/
        val batchQuery = dsl.batch(
            dsl.insertInto(LABEL_EVENT, LABEL_EVENT.EVENT_ID, LABEL_EVENT.LABEL_ID).values("", "")
        )

        labelIdsToInsert.forEach { labelIdToInsert ->
            batchQuery.bind(eventToUpdate.id, labelIdToInsert)
        }

        if (labelIdsToInsert.isNotEmpty()) {
            batchQuery.execute()
        }
    }

    override fun eventsWithFilters(
        labels: Collection<String>,
        categories: Collection<String>,
        names: Collection<String>,
        admins: Collection<String>,
        limit: Int?
    ): Collection<EventResponse> {

        var selectJoin = dsl.select(EVENT.asterisk()).from(EVENT)
        var condition: Condition = trueCondition()

        if (labels.isNotEmpty()) {
            selectJoin = selectJoin.join(LABEL_EVENT).on(LABEL_EVENT.EVENT_ID.eq(EVENT.ID))
                .join(LABEL).on(LABEL.ID.eq(LABEL_EVENT.LABEL_ID))
            condition = condition.and(LABEL.NAME.`in`(labels))
        }

        if (categories.isNotEmpty()) {
            selectJoin = selectJoin.join(CATEGORY).on(CATEGORY.ID.eq(EVENT.CATEGORY_ID))
            condition = condition.and(CATEGORY.NAME.`in`(categories))
        }

        if (names.isNotEmpty()) {
            condition = condition.and(EVENT.NAME.`in`(names))
        }

        if (admins.isNotEmpty()) {
            selectJoin = selectJoin.join(EVENT_ORGANIZER).on(EVENT.ORGANIZER_ID.eq(EVENT_ORGANIZER.ID))
            condition = condition.and(EVENT_ORGANIZER.ADMIN.`in`(admins))
        }

        return selectJoin.where(condition)
            .orderBy(EVENT.START_DATE.asc())
            .limit(limit ?: Int.MAX_VALUE)
            .fetchInto(DBEvent::class.java)
            .mapNotNull { toEventObject(it) }

    }

    override fun findEventsWithLabels(labels: Collection<String>): Collection<EventResponse> {
        if (labels.isEmpty()) {
            return this.findAllEvents()
        }
        return dsl.select(EVENT.asterisk())
            .from(EVENT)
            .leftJoin(LABEL_EVENT).on(LABEL_EVENT.EVENT_ID.eq(EVENT.ID))
            .join(CATEGORY).on(CATEGORY.ID.eq(LABEL_EVENT.LABEL_ID))
            .where(CATEGORY.NAME.`in`(labels))
            .fetchInto(DBEvent::class.java)
            .mapNotNull { toEventObject(it) }
    }

    override fun findAllEvents(): Collection<EventResponse> {
        return dsl.selectFrom(EVENT)
            .fetchInto(DBEvent::class.java)
            .mapNotNull { toEventObject(it) }
    }
}
