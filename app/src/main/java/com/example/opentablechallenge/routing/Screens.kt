package com.example.opentablechallenge.routing

sealed class Screens(val route: String) {
    companion object {
        fun fromRoute(route: String?): Screens {
            return when (route) {
                Reservations.route -> Reservations
                SaveReservation.route -> SaveReservation
                else -> Reservations
            }
        }
    }

    object Reservations : Screens("Reservations")
    object SaveReservation : Screens("SaveReservation")
}
