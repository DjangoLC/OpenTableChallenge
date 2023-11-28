package com.example.opentablechallenge.core.domain.usecases

import app.cash.turbine.test
import com.example.opentablechallenge.core.data.repositories.ReservationsRepositoryImpl
import com.example.opentablechallenge.core.domain.models.BookingDate
import com.example.opentablechallenge.core.domain.models.Reservation
import com.example.opentablechallenge.data.datasources.ReservationsLocalDataSourceImpl
import io.kotest.matchers.collections.shouldContainAll
import io.mockk.MockKAnnotations.init
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import java.time.LocalDateTime

@RunWith(Parameterized::class)
class GetAvailableHoursTest(
    val case: GetAvailableHourCases
) {

    @InjectMockKs
    private lateinit var useCase: GetAvailableHours

    @SpyK
    private var datasource = ReservationsLocalDataSourceImpl

    @SpyK
    private var repository = ReservationsRepositoryImpl(datasource)

    @Before
    fun setup() {
        init(this)
        repository.reset()
    }

    @Test
    fun `given a reservation at 5pm, when get available times, then return the next hour as unable to book`() =
        runBlocking {
            repository.bookReservation(
                Reservation(
                    "name",
                    BookingDate(date = case.date, available = false)
                )
            )
            val values = mutableListOf<BookingDate>()
            useCase.buildUseCase(Unit).test {
                values.addAll(this.awaitItem())
            }
            val mapped = values.filter { !it.available }.map { it.date }
            mapped shouldContainAll case.unableHours
        }

    companion object {
        @JvmStatic
        @Parameters
        fun data(): List<Array<Any>> {
            return listOf(
                arrayOf(
                    GetAvailableHourCases.BookingAtTimeOClock(
                        date = timeOClock.first,
                        unableHours = timeOClock.second
                    )
                ),
                arrayOf(
                    GetAvailableHourCases.BookingAtTimePlus15(
                        date = timePlus15.first,
                        unableHours = timePlus15.second
                    )
                )
            )
        }
    }

}

sealed class GetAvailableHourCases(
    val date: LocalDateTime = LocalDateTime.now(),
    val unableHours: List<LocalDateTime> = emptyList()
) {
    class BookingAtTimeOClock(
        date: LocalDateTime = LocalDateTime.now(),
        unableHours: List<LocalDateTime>
    ) : GetAvailableHourCases(date, unableHours)

    class BookingAtTimePlus15(
        date: LocalDateTime = LocalDateTime.now(),
        unableHours: List<LocalDateTime>
    ) : GetAvailableHourCases(date, unableHours)

    class BookingAtTimePlus30(
        date: LocalDateTime = LocalDateTime.now(),
        unableHours: List<LocalDateTime>
    ) : GetAvailableHourCases(date, unableHours)

    class BookingAtTimePlus45(
        date: LocalDateTime = LocalDateTime.now(),
        unableHours: List<LocalDateTime>
    ) : GetAvailableHourCases(date, unableHours)

}