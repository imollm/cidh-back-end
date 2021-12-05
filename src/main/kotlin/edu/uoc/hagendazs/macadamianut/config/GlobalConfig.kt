package edu.uoc.hagendazs.macadamianut.config

import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import edu.uoc.hagendazs.macadamianut.config.db.ExceptionTranslator
import edu.uoc.hagendazs.macadamianut.config.jackson.TimestampLocalDateTimeDeserializer
import edu.uoc.hagendazs.macadamianut.config.jackson.TimestampLocalDateTimeSerializer
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.core.env.Environment
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import javax.annotation.PostConstruct
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
class GlobalConfig {

    @PostConstruct
    fun setupApplication() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Bean // destroyMethod attribute is used to close the bean
    fun dataSource(environment: Environment): DataSource {
        println("datasource")
        return DataSourceBuilder.create().apply {
            driverClassName(environment.getRequiredProperty("spring.datasource.driverClassName"))
            url(environment.getRequiredProperty("spring.datasource.url"))
            username(environment.getRequiredProperty("spring.datasource.username"))
            password(environment.getRequiredProperty("spring.datasource.password"))
        }.build()
    }

    @Bean
    fun jooqDslBean(
        jooqExecuteListener: DefaultExecuteListener,
        environment: Environment,
        connectionProvider: DataSourceConnectionProvider
    ): DSLContext {
        val configuration = DefaultConfiguration().set(connectionProvider)
            .set(DefaultExecuteListenerProvider(jooqExecuteListener))
            .set(SQLDialect.POSTGRES)

        return DSL.using(configuration)
    }

    @Bean
    fun transactionAwareDataSource(environment: Environment): TransactionAwareDataSourceProxy {
        return TransactionAwareDataSourceProxy(dataSource(environment))
    }

    @Bean
    fun connectionProvider(
        transactionAwareDataSource: TransactionAwareDataSourceProxy
    ): DataSourceConnectionProvider {
        return DataSourceConnectionProvider(transactionAwareDataSource)
    }

    @Bean
    fun transactionManager(environment: Environment): DataSourceTransactionManager {
        return DataSourceTransactionManager(dataSource(environment))
    }

    @Bean
    fun exceptionTransformer(): ExceptionTranslator {
        return ExceptionTranslator()
    }

    @Bean
    fun jacksonObjectMapperBean(): ObjectMapper {

        val mapper = ObjectMapper()
            .registerModule(KotlinModule())
            .registerModule(JavaTimeModule())
        val module = SimpleModule()

        module.addSerializer(LocalDateTime::class.java, TimestampLocalDateTimeSerializer())
        module.addDeserializer(LocalDateTime::class.java, TimestampLocalDateTimeDeserializer())
        mapper.registerModule(module)
        mapper.setTimeZone(TimeZone.getTimeZone("UTC"))
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
        mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);


        val doNotFailOnUnknownFiltersFilter = SimpleFilterProvider().setFailOnUnknownId(false)
        mapper.setFilterProvider(doNotFailOnUnknownFiltersFilter)

        return mapper
    }

    @Bean
    fun encoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean("propertyPlaceholderConfigurer")
    fun propertyPlaceholderConfigurer(): PropertySourcesPlaceholderConfigurer {
        return PropertySourcesPlaceholderConfigurer()
    }
}