package com.example.opentablechallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.opentablechallenge.routing.Screens
import com.example.opentablechallenge.screens.ReservationScreen
import com.example.opentablechallenge.screens.SaveReservationScreen
import com.example.opentablechallenge.ui.theme.OpenTableChallengeTheme

class MainActivity : ComponentActivity() {

    private val reservationsViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OpenTableChallengeTheme {
                val scaffoldState: ScaffoldState = rememberScaffoldState()
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                Scaffold(
                    scaffoldState = scaffoldState,
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButtonPosition = FabPosition.End,
                    floatingActionButton = {
                        if (navBackStackEntry?.destination?.route == Screens.Reservations.route) {
                            FloatingActionButton(
                                onClick = {
                                    navController.navigate(Screens.SaveReservation.route)
                                },
                                contentColor = MaterialTheme.colors.background,
                                content = {
                                    Icon(
                                        imageVector = Icons.Filled.Add,
                                        contentDescription = "Add Reservation Button"
                                    )
                                })
                        }
                    },
                    content = { _ ->
                        MainActivityScreen(
                            navController,
                            reservationsViewModel
                        )
                    }
                )
            }
        }
    }
}


@Composable
private fun MainActivityScreen(
    navController: NavHostController,
    reservationsViewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Reservations.route
    ) {

        composable(Screens.Reservations.route) {
            val reservations by reservationsViewModel.currentReservations.collectAsState(
                initial = emptyList()
            )
            ReservationScreen(
                reservations = reservations
            )
        }

        composable(Screens.SaveReservation.route) {
            val saveReservationViewModel: SaveReservationViewModel = viewModel()

            SaveReservationScreen(
                viewModel = saveReservationViewModel,
                onBackSelected = {
                    navController.popBackStack()
                },
                onSaveReservation = { reservation ->
                    saveReservationViewModel.saveReservation(reservation)
                },
                onTitleChanged = { reservation ->
                    saveReservationViewModel.updateReservation(reservation)
                },
                onDateChanged = { reservation ->
                    saveReservationViewModel.updateReservation(reservation)
                }
            )
        }

    }
}