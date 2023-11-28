package com.example.opentablechallenge.data.datasources

import com.example.opentablechallenge.core.data.datasources.LocalDataSource
import com.example.opentablechallenge.core.domain.models.Reservation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDateTime

object ReservationsLocalDataSourceImpl : LocalDataSource {

    private val helperDate = HelperDate

    private var _cache: MutableMap<LocalDateTime, Reservation?> = mutableMapOf()
    private val _reservationsFlow = MutableStateFlow<List<Reservation>>(emptyList())
    private val _datesFlow = MutableStateFlow<List<LocalDateTime>>(emptyList())

    init {
        helperDate.createDateRange().forEach { date ->
            _cache[date] = null
        }
        updateFlows()
    }

    private fun updateFlows() {
        val reservations = _cache.values.filterNotNull()
        _reservationsFlow.value = reservations
        _datesFlow.value = _cache.keys.toList()
    }

    override fun getReservations(): StateFlow<List<Reservation>> {
        return _reservationsFlow
    }

    override fun bookReservation(reservation: Reservation): Boolean {
        val result = _cache.put(reservation.bookDate.date, reservation)
        updateFlows()
        return result != null
    }

    override fun getRangeOfDates(): StateFlow<List<LocalDateTime>> {
        return _datesFlow
    }

    override fun reset() {
        helperDate.createDateRange().forEach { date ->
            _cache[date] = null
        }
        updateFlows()
    }
}