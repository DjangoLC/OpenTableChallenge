package com.example.opentablechallenge.core.domain.usecases

import androidx.compose.runtime.mutableStateListOf
import app.cash.turbine.test
import com.example.opentablechallenge.core.data.repositories.ReservationsRepositoryImpl
import com.example.opentablechallenge.core.domain.models.BookingDate
import com.example.opentablechallenge.core.domain.models.Reservation
import com.example.opentablechallenge.data.datasources.ReservationsLocalDataSourceImpl
import io.kotest.matchers.collections.shouldBeSortedBy
import io.mockk.MockKAnnotations.init
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetReservationsTest {

    @InjectMockKs
    private lateinit var useCase: GetReservations

    @SpyK
    private var datasource = ReservationsLocalDataSourceImpl

    @SpyK
    private var repository = ReservationsRepositoryImpl(datasource)

    @Before
    fun setup() {
        init(this)
    }

    @Test
    fun `given a reservations call, when reservations are not  empty, then reservations should be sorted by date`(): Unit =
        runBlocking {
            repository.bookReservation(
                Reservation(
                    "name",
                    BookingDate(date = BookingAtTimeOClock, available = false)
                )
            )
            repository.bookReservation(
                Reservation(
                    "name",
                    BookingDate(date = BookingAtTimePlus15, available = false)
                )
            )

            val values = mutableStateListOf<Reservation>()

            useCase.buildUseCase(Unit).test {
                values.addAll(this.awaitItem())
            }

            values.shouldBeSortedBy {
                it.bookDate.date
            }
        }


}