package io.axoniq.handonaxon.conferencetracker.api

import java.time.LocalDate

data class ConferenceAddedEvent(
    val conferenceId: String,
    val name: String,
    val website: String,
    val location: ConferenceLocation,
    val family: String?,
    val ownerEmail: String
)

data class ConferenceEditionAddedEvent(
    val conferenceId: String,
    val editionId: String,
    val year: Int,
    val startDate: LocalDate?,
    val venue: VenueLocation?
)

data class ConferenceEditionScheduledEvent(
    val conferenceId: String,
    val editionId: String,
    val startDate: LocalDate,
)