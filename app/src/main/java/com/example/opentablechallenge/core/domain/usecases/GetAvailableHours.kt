package com.example.opentablechallenge.core.domain.usecases

import com.example.opentablechallenge.core.data.repositories.ReservationsRepository
import com.example.opentablechallenge.core.domain.models.BookingDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.time.LocalDateTime

class GetAvailableHours(
    private val reservationsRepository: ReservationsRepository
) : BaseUseCase<Unit, Flow<List<BookingDate>>>() {

    private val invalidHours = mutableListOf<LocalDateTime>()

    override fun buildUseCase(params: Unit): Flow<List<BookingDate>> {
        invalidHours.clear()
        val availableBookingDates = mutableListOf<BookingDate>()
        val rangeOfDates = reservationsRepository.getAvailableDates().value

        for (date in rangeOfDates) {
            validateHour(date)
            availableBookingDates.add(BookingDate(date = date, available = !invalidHours.contains(date)))
        }

       /* val availableBookingDates2 = rangeOfDates.map { date ->
            validateHour(date)
            BookingDate(date = date, available = !invalidHours.contains(date))
        }*/

        return reservationsRepository.getAvailableDates().map {
            it.map { BookingDate(date = it, available = !invalidHours.contains(it)) }
        }
    }

    private fun validateHour(localDateTime: LocalDateTime) {
        reservationsRepository.getReservations().value.firstOrNull { it.bookDate.date == localDateTime }
            ?.let {
                val nextHours = getNextUnAvailableHours(it.bookDate.date)
                println(nextHours)
                this.invalidHours.addAll(nextHours)
            }
    }

    fun areNextHoursAvailable(localDateTime: LocalDateTime): Boolean {
        val nextSlots = getNextUnAvailableHours(localDateTime).toSet()
        val intersection = nextSlots.intersect(invalidHours.toSet())
        return intersection.isEmpty()
    }

    private fun getNextUnAvailableHours(localDateTime: LocalDateTime): List<LocalDateTime> {
        val rangeOfDates = reservationsRepository.getAvailableDates().value
        val index = rangeOfDates.indexOf(localDateTime)
        val diff = (rangeOfDates.size - index)
        return if (diff >= 4) {
            rangeOfDates.subList(index, index + 4)
        } else {
            rangeOfDates.slice(index until rangeOfDates.size)
        }
    }
}