package com.example.opentablechallenge.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.opentablechallenge.core.domain.models.BookingDate
import com.example.opentablechallenge.core.domain.models.Reservation
import java.time.LocalDateTime

@Composable
fun ReservationRow(
    reservation: Reservation,
) {
    Card(shape = RoundedCornerShape(8.dp)) {
        Column(
            Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(text = reservation.name)
            Text(text = reservation.bookDate.labelReservations)
        }
    }


}

@Composable
@Preview
fun ReservationPreview() {
    ReservationRow(
        reservation = Reservation(
            name = "Sr perez, 4 persons table",
            bookDate = BookingDate(date = LocalDateTime.now(), available =  true)
        )
    )
}