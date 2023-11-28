package com.example.opentablechallenge.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.opentablechallenge.core.domain.models.BookingDate
import com.example.opentablechallenge.core.domain.models.Reservation

@Composable
fun DropDown(
    options: List<BookingDate>,
    optionSelected: (Reservation) -> Unit,
    reservationEntry: Reservation
) {

    val isExpanded = remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(
            onClick = {
                isExpanded.value = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)

        ) {
            Row {
                Text(text = reservationEntry.bookDate.labelSaveReservation)
                Icon(Icons.Default.ArrowDropDown, contentDescription = "")
            }
        }

        DropdownMenu(
            expanded = isExpanded.value,
            onDismissRequest = {
                isExpanded.value = false
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEach { bookingDate ->
                DropdownMenuItem(
                    onClick = {
                        if(bookingDate.available) {
                            isExpanded.value = false
                            optionSelected.invoke(reservationEntry.copy(bookDate = bookingDate))
                        }
                    }) {
                    Text(text = bookingDate.labelSaveReservation)
                }
            }

        }
    }
}

