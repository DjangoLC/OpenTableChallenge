package com.example.opentablechallenge.core.domain.usecases

import com.example.opentablechallenge.core.data.repositories.ReservationsRepository
import com.example.opentablechallenge.core.domain.models.Reservation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetReservations(
    private val reservationsRepository: ReservationsRepository
) : BaseUseCase<Unit, Flow<List<Reservation>>>() {
    override fun buildUseCase(params: Unit): Flow<List<Reservation>> {
        return reservationsRepository.getReservations().map {
            it.sortedBy { it.bookDate.date }
        }
    }

}