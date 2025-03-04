package com.amirnlz.tasko.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset


fun Int.greetingMessageByHour(): String {
    return when (this) {
        in 12..16 -> {
            "Good Afternoon";
        }

        in 17..20 -> {
            "Good Evening";
        }

        in 21..23 -> {
            "Good Night";
        }

        else -> {
            "Good Morning";
        }
    }
}

fun Long?.toLocalDateFromMillis(): LocalDate? {
    return this?.let {
        Instant.ofEpochMilli(it)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
    }
}


fun LocalDate.toEpochMilli() : Long{
    return this.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
}