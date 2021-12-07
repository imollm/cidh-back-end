package edu.uoc.hagendazs.macadamianut.config.jackson

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import java.lang.IllegalArgumentException
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class TimestampLocalDateTimeDeserializer: StdDeserializer<LocalDateTime>(LocalDateTime::class.java) {
    override fun deserialize(jsonparser: JsonParser?, ctxt: DeserializationContext?): LocalDateTime {
        val timestamp = jsonparser?.numberValue?.toDouble()?.times(1000)?.toLong()
        timestamp ?: kotlin.run {
            throw IllegalArgumentException("Unsupported field time format. Expected timestamp. " +
                    "Field value: ${jsonparser?.text}")
        }
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.of("UTC"))
    }
}