package com.amirnlz.tasko.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId


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
    return this?.let { milliseconds ->
        Instant.ofEpochMilli(milliseconds)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }
}