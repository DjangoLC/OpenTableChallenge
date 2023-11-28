package com.example.opentablechallenge

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.opentablechallenge.core.data.repositories.ReservationsRepositoryImpl
import com.example.opentablechallenge.core.domain.models.BookingDate
import com.example.opentablechallenge.core.domain.models.Reservation
import com.example.opentablechallenge.core.domain.usecases.GetAvailableHours
import com.example.opentablechallenge.core.domain.usecases.SaveReservation
import com.example.opentablechallenge.data.datasources.HelperDate
import com.example.opentablechallenge.data.datasources.ReservationsLocalDataSourceImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime

class SaveReservationViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    companion object {
        val DEFAULT = Reservation(
            name = "",
            bookDate = BookingDate(date = HelperDate.MIN_DATE, available = true)
        )
    }

    private val reservationsLocalDataSource = ReservationsLocalDataSourceImpl
    private val reservationsRepository = ReservationsRepositoryImpl(reservationsLocalDataSource)
    private val getAvailableHours = GetAvailableHours(reservationsRepository)
    private val saveReservation = SaveReservation(reservationsRepository)

    private var _availableHours = getAvailableHours.buildUseCase(Unit)
    val availableHours: Flow<List<BookingDate>> = _availableHours

    private var _reservationEntry = MutableStateFlow(DEFAULT)
    val reservationEntry: StateFlow<Reservation> = _reservationEntry.asStateFlow()

    private var _isValidForm = MutableStateFlow(false)
    val isValidForm: StateFlow<Boolean> = _isValidForm.asStateFlow()

    fun setup() {
        _availableHours = getAvailableHours.buildUseCase(Unit)
    }

    fun saveReservation(reservation: Reservation) {
        validateForm(reservation)
        saveReservation.buildUseCase(
            SaveReservation.Params(
                reservation.name,
                reservation.bookDate.date
            )
        )
        setup()
    }

    private fun validateForm(reservation: Reservation) {
        if (reservation.name.isEmpty()) {
            _isValidForm.value = false
        } else _isValidForm.value = getAvailableHours.areNextHoursAvailable(reservation.bookDate.date)
    }

    fun updateReservation(reservation: Reservation) {
        _reservationEntry.value = reservation
        validateForm(reservation)
    }

}