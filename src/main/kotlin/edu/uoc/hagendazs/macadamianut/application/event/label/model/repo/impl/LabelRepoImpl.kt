package edu.uoc.hagendazs.macadamianut.application.event.label.model.repo.impl

import edu.uoc.hagendazs.generated.jooq.tables.references.LABEL
import edu.uoc.hagendazs.generated.jooq.tables.references.LABEL_EVENT
import edu.uoc.hagendazs.macadamianut.application.event.label.model.dataclasses.EventLabel
import edu.uoc.hagendazs.macadamianut.application.event.label.model.repo.LabelRepo
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class LabelRepoImpl: LabelRepo {

    @Autowired
    protected lateinit var dsl: DSLContext

    override fun findByName(labels: Collection<String>): Collection<EventLabel> {
        return dsl.selectFrom(LABEL)
            .where(LABEL.NAME.`in`(labels))
            .fetchInto(EventLabel::class.java)
    }
}