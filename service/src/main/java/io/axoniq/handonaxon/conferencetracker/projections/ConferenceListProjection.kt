package io.axoniq.handonaxon.conferencetracker.projections

import io.axoniq.handonaxon.conferencetracker.api.AllConferencesQuery
import io.axoniq.handonaxon.conferencetracker.api.ConferenceAddedEvent
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("query")
@Component
class ConferenceListProjection(val repository: ConferenceRepository) {

    @EventHandler
    fun handle(event : ConferenceAddedEvent) {
        repository.save(
            Conference(
                conferenceId = event.conferenceId,
                name = event.name,
                website = event.website
            )
        )
    }


    @QueryHandler
    fun handle(query : AllConferencesQuery): List<String> {
        return repository.findAll().map { it.name }
    }
}