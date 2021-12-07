package edu.uoc.hagendazs.macadamianut.config.db

import mu.KotlinLogging
import org.jooq.ExecuteContext
import org.jooq.impl.DefaultExecuteListener
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator

class ExceptionTranslator : DefaultExecuteListener() {

    private val logger = KotlinLogging.logger {}

    override fun exception(context: ExecuteContext) {
        val dialect = context.configuration().dialect()
        val translator = SQLErrorCodeSQLExceptionTranslator(dialect.name)
        val taskName = "Access database using Jooq"
        val sqlException = context.sqlException() ?: run {
            logger.error { "Unable to translate null SQL Exception!. Query -> ${context.sql()}" }
            return
        }
        context.exception(
            translator
                .translate(taskName, context.sql(), sqlException)
        )
    }
}