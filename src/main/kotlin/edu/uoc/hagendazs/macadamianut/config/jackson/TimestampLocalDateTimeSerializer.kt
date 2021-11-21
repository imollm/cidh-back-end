package edu.uoc.hagendazs.macadamianut.config.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.time.LocalDateTime
import java.time.ZoneId

class TimestampLocalDateTimeSerializer : StdSerializer<LocalDateTime>(LocalDateTime::class.java) {
    override fun serialize(value: LocalDateTime?, gen: JsonGenerator?, sp: SerializerProvider?) {
        val epoch = value?.atZone(ZoneId.of("UTC"))?.toInstant()?.epochSecond
        when {
            epoch != null -> gen?.writeNumber(epoch)
            else -> gen?.writeNull()
        }
    }
}