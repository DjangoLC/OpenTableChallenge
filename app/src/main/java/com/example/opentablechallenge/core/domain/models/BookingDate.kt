package com.example.opentablechallenge.core.domain.models

import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.UUID

data class BookingDate(
    val uuid: String = UUID.randomUUID().toString(),
    val date: LocalDateTime,
    val available: Boolean = true
) : Serializable {

    val labelReservations: String
        get() {
            return DateTimeFormatter.ofPattern("hh:mm a", Locale.US).format(date)
        }


    val labelSaveReservation: String
        get() {
            return if (available) {
                labelReservations
            } else labelReservations.plus(" - Not available")
        }

}