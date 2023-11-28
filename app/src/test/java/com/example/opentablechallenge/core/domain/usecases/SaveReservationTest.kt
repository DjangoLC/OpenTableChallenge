package com.example.opentablechallenge.core.domain.usecases

import app.cash.turbine.test
import com.example.opentablechallenge.core.data.repositories.ReservationsRepositoryImpl
import com.example.opentablechallenge.core.domain.models.BookingDate
import com.example.opentablechallenge.core.domain.models.Reservation
import com.example.opentablechallenge.data.datasources.ReservationsLocalDataSourceImpl
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SaveReservationTest {

    @InjectMockKs
    private lateinit var useCase: SaveReservation

    @InjectMockKs
    private lateinit var getAvailableHoursUseCase: GetAvailableHours

    @SpyK
    private var datasource = ReservationsLocalDataSourceImpl

    @SpyK
    private var repository = ReservationsRepositoryImpl(datasource)

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository.reset()
    }

    @Test
    fun `given a save reservation call, when reservations its called, then reservations should be returned`(): Unit =
        runBlocking {
            useCase.buildUseCase(SaveReservation.Params("name", BookingAtTimeOClock))
            val values = mutableListOf<Reservation>()

            repository.getReservations().test {
                values.addAll(this.awaitItem())
            }
            values.first {
                it.bookDate.date == BookingAtTimeOClock && it.name == "name"
            }.shouldNotBeNull()
        }

    @Test
    fun `given a save reservation call, when reservations its called, then next time slots should be unavailable`() =
        runBlocking {
            useCase.buildUseCase(SaveReservation.Params("name", BookingAtTimeOClock))
            val reservations = mutableListOf<Reservation>()
            val availableSlots = mutableListOf<BookingDate>()

            getAvailableHoursUseCase.buildUseCase(Unit).test {
                availableSlots.addAll(this.awaitItem())
            }

            repository.getReservations().test {
                reservations.addAll(this.awaitItem())
            }

            reservations.first {
                it.bookDate.date == BookingAtTimeOClock && it.name == "name"
            }.shouldNotBeNull()

            availableSlots.filter { !it.available }.should {
                it.size.shouldBe(4)
                it.map { bookingDate -> bookingDate.date }.shouldContainAll(timeOClock.second)
            }
        }
}