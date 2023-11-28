package com.example.opentablechallenge.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.opentablechallenge.ReservationPlaceholder
import com.example.opentablechallenge.SaveReservationViewModel
import com.example.opentablechallenge.components.DropDown
import com.example.opentablechallenge.components.TopAppBar
import com.example.opentablechallenge.core.domain.models.BookingDate
import com.example.opentablechallenge.core.domain.models.Reservation
import java.time.LocalDateTime

@Composable
fun SaveReservationScreen(
    viewModel: SaveReservationViewModel,
    onTitleChanged: (Reservation) -> Unit,
    onDateChanged: (Reservation) -> Unit,
    onBackSelected: () -> Unit,
    onSaveReservation: (Reservation) -> Unit
) {

    val reservationEntry: Reservation by viewModel.reservationEntry
        .collectAsState()

    val availableHours: List<BookingDate> by viewModel.availableHours
        .collectAsState(initial = emptyList())

    val isValidForm: Boolean by viewModel.isValidForm
        .collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = "New Reservation", icon = Icons.Filled.ArrowBack) {
                onBackSelected.invoke()
            }
        },
        content = {
            Column(
                Modifier
                    .fillMaxHeight()
                    .padding(it)
            ) {
                Reservation2(
                    reservationEntry,
                    availableHours,
                    isValidForm,
                    onTitleChanged,
                    onDateChanged,
                    onSaveReservation,
                    onBackSelected
                )
            }
        }
    )
}

@Composable
fun Reservation2(
    reservationEntry: Reservation,
    availableHours: List<BookingDate>,
    isValidForm: Boolean,
    onTitleChanged: (Reservation) -> Unit,
    onDateChanged: (Reservation) -> Unit,
    onSaveReservation: (Reservation) -> Unit,
    onBackSelected: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        TextField(
            value = reservationEntry.name,
            onValueChange = {
                onTitleChanged.invoke(reservationEntry.copy(name = it))
            },
            label = {
                Text(text = "Name of the reservation")
            },
            modifier = Modifier.fillMaxWidth()
        )
        DropDown(availableHours, optionSelected = {
            onDateChanged.invoke(reservationEntry.copy(bookDate = it.bookDate))
        }, reservationEntry)

        if (!isValidForm) {
            Text(text = "Date not available please select another date")
        }
        Button(
            enabled = isValidForm,
            onClick = {
                onSaveReservation(reservationEntry.copy())
                onBackSelected.invoke()
            }, content = {
                Text(text = "Save reservation")
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Preview
@Composable
fun Reservation2Preview() {
    Reservation2(
        Reservation("", BookingDate(date = LocalDateTime.now(), available = false)),
        ReservationPlaceholder.createReservations().map { it.bookDate }, true, {}, {}, {}, {}
    )
}

