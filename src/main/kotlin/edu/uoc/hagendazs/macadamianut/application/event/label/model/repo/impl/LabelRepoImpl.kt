package edu.uoc.hagendazs.macadamianut.application.event.label.model.repo.impl

import edu.uoc.hagendazs.macadamianut.application.event.label.model.dataClass.Label
import org.springframework.stereotype.Repository
import edu.uoc.hagendazs.macadamianut.application.event.label.model.repo.LabelRepo
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import edu.uoc.hagendazs.generated.jooq.tables.references.LABEL
import org.springframework.transaction.annotation.Transactional

@Repository
class LabelRepoImpl : LabelRepo {

    @Autowired
    protected lateinit var dsl: DSLContext

    @Transactional
    override fun addLabel(label: Label): Label? {
        val labelRecord = dsl.newRecord(LABEL, label)
        labelRecord.store()

        return this.findByName(label.name)
    }

    override fun updateLabel(label: Label): Label? {
        dsl.update(LABEL)
            .set(LABEL.NAME, label.name)
            .set(LABEL.DESCRIPTION, label.description)
            .where(LABEL.ID.eq(label.id))
            .execute()

        return this.findById(label.id)
    }

    override fun showLabel(labelId: String): Label? {
        return this.findById(labelId)
    }

    override fun listAllCategories(): Collection<Label> {
        return dsl.selectFrom(LABEL).fetchInto(Label::class.java)
    }

    override fun existsByName(labelName: String): Boolean {
        return dsl.fetchExists(
            dsl.selectFrom(LABEL)
                .where(LABEL.NAME.eq(labelName))
        )
    }

    override fun findByName(labelName: String): Label? {
        return dsl.selectFrom(LABEL)
            .where(LABEL.NAME.eq(labelName))
            .fetchOne()
            ?.into(Label::class.java)
    }

    override fun findById(labelId: String): Label? {
        return dsl.selectFrom(LABEL)
            .where(LABEL.ID.eq(labelId))
            .fetchOne()
            ?.into(Label::class.java)
    }

    override fun removeLabelById(labelId: String): Boolean {
        val deleted = dsl.deleteFrom(LABEL)
            .where(LABEL.ID.eq(labelId))
            .execute()

        return deleted > 0
    }
}
