package edu.uoc.hagendazs.macadamianut.common.kotlin

inline fun <reified T : Enum<*>> valueOfIgnoreCase(name: String?): T? =
    name?.runCatching {
        val mapToVal = enumValues<T>().associateBy { it.toString().lowercase() }
        mapToVal[name.lowercase()]
    }?.getOrNull()