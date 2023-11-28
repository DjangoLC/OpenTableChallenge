package com.example.opentablechallenge.core.domain.usecases

import java.time.LocalDateTime

val BookingAtTimeOClock = LocalDateTime.of(2023, 11, 28, 17, 0)

val timeOClock = Pair<LocalDateTime, List<LocalDateTime>>(
    BookingAtTimeOClock, listOf(
        LocalDateTime.of(2023, 11, 28, 17, 0),
        LocalDateTime.of(2023, 11, 28, 17, 15),
        LocalDateTime.of(2023, 11, 28, 17, 30),
        LocalDateTime.of(2023, 11, 28, 17, 45)
    )
)

val BookingAtTimePlus15 = LocalDateTime.of(2023, 11, 28, 18, 15)

val timePlus15 = Pair<LocalDateTime, List<LocalDateTime>>(
    BookingAtTimePlus15, listOf(
        LocalDateTime.of(2023, 11, 28, 18, 15),
        LocalDateTime.of(2023, 11, 28, 18, 30),
        LocalDateTime.of(2023, 11, 28, 18, 45),
        LocalDateTime.of(2023, 11, 28, 19, 0)
    )
)