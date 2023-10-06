package io.axoniq.handonaxon.conferencetracker.projections

import io.axoniq.handonaxon.conferencetracker.api.AllConferencesQuery
import io.axoniq.handonaxon.conferencetracker.api.ConferenceAddedEvent
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component

@Component
class ConferenceListProjection {

    private val conferences = mutableListOf<String>()

    @EventHandler
    fun handle(event : ConferenceAddedEvent) {
        conferences.add(event.name)
    }


    @QueryHandler
    fun handle(query : AllConferencesQuery): List<String> {
        return conferences.toList()
    }
}