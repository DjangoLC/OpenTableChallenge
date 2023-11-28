package com.example.opentablechallenge.core.data.datasources

import com.example.opentablechallenge.core.domain.models.Reservation
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDateTime

interface LocalDataSource {
    fun getReservations(): StateFlow<List<Reservation>>
    fun bookReservation(reservation: Reservation): Boolean
    fun getRangeOfDates(): StateFlow<List<LocalDateTime>>
    fun reset()
}