package com.example.opentablechallenge

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.opentablechallenge.core.data.repositories.ReservationsRepositoryImpl
import com.example.opentablechallenge.core.domain.usecases.GetReservations
import com.example.opentablechallenge.data.datasources.ReservationsLocalDataSourceImpl

class MainViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val reservationsLocalDataSource = ReservationsLocalDataSourceImpl
    private val reservationsRepository = ReservationsRepositoryImpl(reservationsLocalDataSource)
    private val getReservations = GetReservations(reservationsRepository)


    val currentReservations = getReservations.buildUseCase(Unit)
}
