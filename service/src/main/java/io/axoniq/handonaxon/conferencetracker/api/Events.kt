package io.axoniq.handonaxon.conferencetracker.api

data class ConferenceAddedEvent(
    val conferenceId: String,
    val name: String,
    val website: String,
    val location: ConferenceLocation,
    val family: String?
)
