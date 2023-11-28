package com.example.opentablechallenge.core.data.repositories

import com.example.opentablechallenge.core.domain.models.Reservation
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDateTime

interface ReservationsRepository {
    fun getReservations(): StateFlow<List<Reservation>>
    fun bookReservation(reservation: Reservation): Boolean
    fun getAvailableDates(): StateFlow<List<LocalDateTime>>
    fun reset()
}