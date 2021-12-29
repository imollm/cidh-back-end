package edu.uoc.hagendazs.macadamianut.application.event.event.model.repo.impl

import com.fasterxml.jackson.databind.ObjectMapper
import edu.uoc.hagendazs.generated.jooq.tables.references.*
import edu.uoc.hagendazs.macadamianut.application.event.category.model.repo.CategoryRepo
import edu.uoc.hagendazs.macadamianut.application.event.event.entrypoint.input.NewOrUpdateEventRequest
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

    override fun findById(id: String, requesterUserId: String?): EventResponse? {
        val dbEvent = dsl.selectFrom(EVENT)
            .where(EVENT.ID.eq(id))
            .fetchOne()
            ?.into(DBEvent::class.java)

        val rawFetch = dsl.selectFrom(EVENT)
            .where(EVENT.ID.eq(id))
            .fetchOne()

        return toEventObject(dbEvent, requesterUserId)
    }

    override fun findByName(name: String, requesterUserId: String?): EventResponse? {
        val dbEvent = dsl.selectFrom(EVENT)
            .where(EVENT.NAME.eq(name))
            .fetchOne()
            ?.into(DBEvent::class.java)

        return toEventObject(dbEvent, requesterUserId)
    }

    private fun toEventObject(dbEvent: DBEvent?, requesterUserId: String?): EventResponse? {
        dbEvent ?: return null
        val category = categoryRepo.findById(dbEvent.categoryId) ?: run {
            throw IllegalStateException("Category cannot be null")
        }
        val labels = labelRepo.labelsForEvent(dbEvent.id)
        val eventOrganizer = eventOrganizerRepo.getEventOrganizer(dbEvent.organizerId) ?: run {
            throw IllegalStateException("Event Organizer not found")
        }
        val rating = mediaRepo.ratingForEvent(dbEvent.id) ?: run {
            throw IllegalStateException("Rating not found")
        }

        val isFavorite = mediaRepo.isFavoriteEventForUserId(dbEvent.id, requesterUserId)

        return dbEvent.toEventResponse(
            rating = rating,
            category = category,
            labels = labels,
            eventOrganizer = eventOrganizer,
            isFavorite = isFavorite,
        )
    }

    override fun create(newEvent: DBEvent, labelIds: Collection<String>): EventResponse? {
        val eventRecord = dsl.newRecord(EVENT, newEvent)
        eventRecord.store()
        insertLabelsForEvent(labelIds, newEvent.id)
        return this.findById(newEvent.id, null)
    }

    @Transactional
    override fun update(
        eventId: String,
        updateEventRequest: NewOrUpdateEventRequest,
        requesterUserId: String?,
        categoryId: String?
    ): EventResponse? {
        dsl.update(EVENT)
            .set(EVENT.NAME, updateEventRequest.name)
            .set(EVENT.DESCRIPTION, updateEventRequest.description)
            .set(EVENT.HEADER_IMAGE, updateEventRequest.headerImage.toString())
            .set(EVENT.START_DATE, updateEventRequest.startDate)
            .set(EVENT.END_DATE, updateEventRequest.endDate)
            .set(EVENT.CATEGORY_ID, categoryId)
            .where(EVENT.ID.eq(eventId))
            .execute()

        val existingLabelIds = dsl.select(LABEL_EVENT.LABEL_ID)
            .from(LABEL_EVENT)
            .where(LABEL_EVENT.LABEL_ID.`in`(updateEventRequest.labelIds))
            .fetchInto(String::class.java)
            .toSet()

        val labelIdsToInsert = updateEventRequest.labelIds.minus(existingLabelIds)
        insertLabelsForEvent(labelIdsToInsert, eventId)

        val labelsToDelete = existingLabelIds.minus(updateEventRequest.labelIds.toSet())
        deleteLabelsForEvent(labelsToDelete, eventId)

        return findById(eventId, requesterUserId)
    }

    private fun deleteLabelsForEvent(labelsToDelete: Set<String>, eventId: String) {
        dsl.deleteFrom(LABEL_EVENT)
            .where(LABEL_EVENT.EVENT_ID.eq(eventId))
            .and(LABEL_EVENT.LABEL_ID.`in`(labelsToDelete))
            .execute()
    }

    private fun insertLabelsForEvent(
        labelIdsToInsert: Collection<String>,
        eventId: String
    ) {
        // jOOQ requires dummy bind values for the original query
        // https://www.jooq.org/doc/3.15/manual/sql-execution/batch-execution/
        val batchQuery = dsl.batch(
            dsl.insertInto(LABEL_EVENT, LABEL_EVENT.EVENT_ID, LABEL_EVENT.LABEL_ID).values("", "")
        )

        labelIdsToInsert.forEach { labelIdToInsert ->
            batchQuery.bind(eventId, labelIdToInsert)
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
        limit: Int?,
        requesterUserId: String?
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
            .mapNotNull { toEventObject(it, requesterUserId) }

    }

    override fun findEventsWithLabels(labels: Collection<String>, requesterUserId: String?): Collection<EventResponse> {
        if (labels.isEmpty()) {
            return this.findAllEvents(requesterUserId)
        }
        return dsl.select(EVENT.asterisk())
            .from(EVENT)
            .leftJoin(LABEL_EVENT).on(LABEL_EVENT.EVENT_ID.eq(EVENT.ID))
            .join(CATEGORY).on(CATEGORY.ID.eq(LABEL_EVENT.LABEL_ID))
            .where(CATEGORY.NAME.`in`(labels))
            .fetchInto(DBEvent::class.java)
            .mapNotNull { toEventObject(it, requesterUserId) }
    }

    override fun findAllEvents(requesterUserId: String?): Collection<EventResponse> {
        return dsl.selectFrom(EVENT)
            .fetchInto(DBEvent::class.java)
            .mapNotNull { toEventObject(it, requesterUserId) }
    }
}
