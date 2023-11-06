package io.axoniq.handonaxon.conferencetracker.model

import io.axoniq.handonaxon.conferencetracker.api.*
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.matchers.IgnoreField
import org.axonframework.test.matchers.Matchers
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ConferenceTest {

    val testFixture = AggregateTestFixture(Conference::class.java)

    @Test
    fun `Test AddConferenceCommand creates aggregate`() {
        //given - when - then
        testFixture.givenNoPriorActivity()
            .`when`(
                AddConferenceCommand(
                    name = "DrivUn", ownerEmail = "dave@gmail.com", website = "https://drivun.co",
                    location = ConferenceLocation(city = "Amsterdam", country = "The Netherlands", online = false),
                    family = null
                )
            ).expectEventsMatching(
                Matchers.payloadsMatching(Matchers.exactSequenceOf(
                    Matchers.deepEquals(
                        ConferenceAddedEvent(name = "DrivUn", website = "https://drivun.co",
                            location = ConferenceLocation(city = "Amsterdam", country = "The Netherlands", online = false),
                            family = null, ownerEmail = "dave@gmail.com", conferenceId = "ddd"),
                        IgnoreField(ConferenceAddedEvent::class.java, "conferenceId"))
                    ),
                )
            )


    }
    @Test
    fun `Test Can Not add conference edition if not the owner`() {
        val id  = "NOT_AN_OWNER_CONFERENCE"
        testFixture.given(listOf(
            ConferenceAddedEvent(name = "DrivUn", website = "https://drivun.co",
                location = ConferenceLocation(city = "Amsterdam", country = "The Netherlands", online = false),
                family = null, ownerEmail = "dave@gmail.com", conferenceId = id)
        ))
            .`when`(AddConferenceEditionCommand(
                conferenceId = id,
                year = 1994,
                startDate = LocalDate.of(1994, 1,29),
                venue = VenueLocation("St Clara Hospital", "Somewhere"),
                ownerEmail = "mitchell@gmail.com"
            ))
            .expectExceptionMessage("User is not authorized to add edition to the conference NOT_AN_OWNER_CONFERENCE, only the owner can do it")
            .expectNoEvents()

    }
}