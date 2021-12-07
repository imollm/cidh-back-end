package edu.uoc.hagendazs.macadamianut.config.spring

import edu.uoc.hagendazs.macadamianut.application.user.model.dataClass.RoleEnum
import edu.uoc.hagendazs.macadamianut.common.kotlin.valueOfIgnoreCase
import org.springframework.core.convert.converter.Converter

class RoleEnumConverter: Converter<String,  RoleEnum> {

    override fun convert(source: String): RoleEnum? {
        return valueOfIgnoreCase(source)
    }
}