package edu.uoc.hagendazs.macadamianut.application.event.event.model.repo.impl

import com.fasterxml.jackson.databind.ObjectMapper
import edu.uoc.hagendazs.generated.jooq.tables.references.CATEGORY
import edu.uoc.hagendazs.generated.jooq.tables.references.EVENT
import edu.uoc.hagendazs.generated.jooq.tables.references.LABEL
import edu.uoc.hagendazs.generated.jooq.tables.references.LABEL_EVENT
import edu.uoc.hagendazs.macadamianut.application.event.event.model.dataClass.CIDHEvent
import edu.uoc.hagendazs.macadamianut.application.event.event.model.repo.EventRepo
import edu.uoc.hagendazs.macadamianut.application.event.label.model.repo.LabelRepo
import mu.KotlinLogging
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.impl.DSL.trueCondition
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class EventRepoImpl : EventRepo {

    @Autowired
    protected lateinit var labelRepo: LabelRepo

    @Autowired
    protected lateinit var dsl: DSLContext

    @Autowired
    lateinit var objectMapper: ObjectMapper

    private val logger = KotlinLogging.logger {}

    override fun findByName(name: String): CIDHEvent? {
        return dsl.selectFrom(EVENT)
            .where(EVENT.NAME.eq(name))
            .fetchOne()
            ?.into(CIDHEvent::class.java)
    }

    override fun create(newEvent: CIDHEvent): CIDHEvent? {
        val eventRecord = dsl.newRecord(EVENT, newEvent)
        eventRecord.store()

        return this.findById(newEvent.id)
    }

    override fun findById(id: String): CIDHEvent? {
        return dsl.selectFrom(EVENT)
            .where(EVENT.ID.eq(id))
            .fetchOne()
            ?.into(CIDHEvent::class.java)
    }

    override fun update(eventToUpdate: CIDHEvent): CIDHEvent? {
        dsl.update(EVENT)
            .set(EVENT.NAME, eventToUpdate.name)
            .set(EVENT.DESCRIPTION, eventToUpdate.description)
            .set(EVENT.HEADER_IMAGE, eventToUpdate.headerImage.toString())
            .set(EVENT.START_DATE, eventToUpdate.startDate)
            .set(EVENT.END_DATE, eventToUpdate.endDate)
            .set(EVENT.CATEGORY_ID, eventToUpdate.categoryId)
            .execute()
        return findById(eventToUpdate.id)
    }

    override fun eventsWithFilters(
        labels: Collection<String>,
        categories: Collection<String>,
        names: Collection<String>,
        limit: Int?
    ): Collection<CIDHEvent> {

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

        return selectJoin.where(condition)
            .orderBy(EVENT.START_DATE.asc())
            .limit(limit ?: Int.MAX_VALUE)
            .fetchInto(CIDHEvent::class.java)

    }

    override fun findEventsWithLabels(labels: Collection<String>): Collection<CIDHEvent> {
        if (labels.isEmpty()) {
            return this.findAllEvents()
        }
        return dsl.select(EVENT.asterisk())
            .from(EVENT)
            .leftJoin(LABEL_EVENT).on(LABEL_EVENT.EVENT_ID.eq(EVENT.ID))
            .join(CATEGORY).on(CATEGORY.ID.eq(LABEL_EVENT.LABEL_ID))
            .where(CATEGORY.NAME.`in`(labels))
            .fetchInto(CIDHEvent::class.java)
    }

    override fun findAllEvents(): Collection<CIDHEvent> {
        return dsl.selectFrom(EVENT).fetchInto(CIDHEvent::class.java)
    }
}
