package io.axoniq.handonaxon.conferencetracker.api

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.LocalDate

data class AddConferenceCommand(
    val name: String,
    val website: String,
    val location: ConferenceLocation,
    val family: String?
)

data class AddConferenceEditionCommand(
    @TargetAggregateIdentifier
    val conferenceId: String,
    val year: Int,
    val startDate: LocalDate?,
    val venue: VenueLocation?
)

data class ScheduleConferenceEditionCommand(
    @TargetAggregateIdentifier
    val conferenceId: String,
    val editionId: String,
    val startDate: LocalDate,
)

data class VenueLocation(
    val name: String,
    val address: String
)

data class ConferenceLocation(
    val country: String?,
    val city: String?,
    val online: Boolean
)
