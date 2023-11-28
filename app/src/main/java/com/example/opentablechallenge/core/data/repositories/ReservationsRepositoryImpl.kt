package com.example.opentablechallenge.core.data.repositories

import com.example.opentablechallenge.core.data.datasources.LocalDataSource
import com.example.opentablechallenge.core.domain.models.Reservation
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDateTime

class ReservationsRepositoryImpl(
    private val localDataSource: LocalDataSource
) : ReservationsRepository {
    override fun getReservations(): StateFlow<List<Reservation>> {
        return localDataSource.getReservations()
    }

    override fun bookReservation(reservation: Reservation): Boolean {
        return localDataSource.bookReservation(reservation)
    }

    override fun getAvailableDates(): StateFlow<List<LocalDateTime>> {
        return localDataSource.getRangeOfDates()
    }

    override fun reset() {
        localDataSource.reset()
    }

}