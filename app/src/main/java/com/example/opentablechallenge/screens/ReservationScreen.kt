package com.example.opentablechallenge.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.example.opentablechallenge.ReservationPlaceholder
import com.example.opentablechallenge.components.ReservationList
import com.example.opentablechallenge.components.TopAppBar
import com.example.opentablechallenge.core.domain.models.Reservation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun ReservationScreen(
    reservations: List<Reservation>
) {

    Column(verticalArrangement = Arrangement.SpaceBetween) {
        TopAppBar(title = "Reservations") {

        }
        if (reservations.isNotEmpty()) {
            ReservationList(
                reservationList = reservations
            )
        } else {
            Text(text = "you don't have any reservation yet")
        }
    }
}

@Preview
@Composable
fun ReservationScreenPreview() {
    ReservationScreen(reservations = ReservationPlaceholder.createReservations())
    //ReservationScreen(reservations = MutableState<List<Reservation>>(emptyList<Reservation>()))
}
