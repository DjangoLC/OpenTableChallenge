package com.example.opentablechallenge

import com.example.opentablechallenge.core.domain.models.BookingDate
import com.example.opentablechallenge.core.domain.models.Reservation
import java.time.LocalDateTime
import java.util.Date

object ReservationPlaceholder {

    val SINGLE = listOf(Reservation("Reservation", BookingDate(date = LocalDateTime.now(), available =  true)))

    fun createReservations(size: Int = 10): List<Reservation> {
        val reservationList = mutableListOf<Reservation>()
        for (i in 1..size) {
            reservationList.add(
                Reservation(
                    "Reservation #$i",
                    BookingDate(date = LocalDateTime.now(), available =  true)
                )
            )
        }
        return reservationList
    }

}