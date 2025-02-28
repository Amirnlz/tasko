package com.amirnlz.tasko.utils


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