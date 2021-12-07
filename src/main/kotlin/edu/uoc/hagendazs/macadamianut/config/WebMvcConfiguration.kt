package edu.uoc.hagendazs.macadamianut.config

import edu.uoc.hagendazs.macadamianut.config.spring.RoleEnumConverter
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.http.MediaType
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableScheduling
class WebMvcConfiguration: WebMvcConfigurer {
    override fun configureContentNegotiation(configurer: ContentNegotiationConfigurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON)
    }

    override fun addFormatters(registry: FormatterRegistry) {
        //Converter used to support insensitive strings -> Enum types through the REST API
        registry.addConverter(RoleEnumConverter())
    }
}