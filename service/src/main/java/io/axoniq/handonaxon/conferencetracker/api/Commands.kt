package io.axoniq.handonaxon.conferencetracker.api

data class AddConferenceCommand (val name: String, val website:String, val location: ConferenceLocation, val family: String?)

data class ConferenceLocation (val country: String?, val city: String?, val online: Boolean)
