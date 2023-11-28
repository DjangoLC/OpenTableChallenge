package com.example.opentablechallenge.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.opentablechallenge.ReservationPlaceholder
import com.example.opentablechallenge.core.domain.models.Reservation

@Composable
fun ReservationList(
    reservationList: List<Reservation>
) {

    LazyColumn {
        items(reservationList) { reservation ->
            ReservationRow(reservation = reservation)
        }
    }

}

@Composable
@Preview
fun ReservationListPreview() {
    ReservationList(reservationList = ReservationPlaceholder.createReservations())
}