package com.example.opentablechallenge.core.domain.usecases

import com.example.opentablechallenge.core.data.repositories.ReservationsRepository
import com.example.opentablechallenge.core.domain.models.BookingDate
import com.example.opentablechallenge.core.domain.models.Reservation
import java.time.LocalDateTime

class SaveReservation(
    private val repository: ReservationsRepository
) : BaseUseCase<SaveReservation.Params, Boolean>() {


    override fun buildUseCase(params: Params): Boolean {
        return repository.bookReservation(Reservation(params.name, BookingDate(date = params.localDateTime, available = false)))
    }

    class Params(val name: String, val localDateTime: LocalDateTime)

}