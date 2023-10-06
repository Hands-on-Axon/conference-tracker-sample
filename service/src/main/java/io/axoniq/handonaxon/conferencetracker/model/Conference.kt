package io.axoniq.handonaxon.conferencetracker.model

import io.axoniq.handonaxon.conferencetracker.api.AddConferenceCommand
import io.axoniq.handonaxon.conferencetracker.api.ConferenceAddedEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.common.IdentifierFactory
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class Conference {

    @AggregateIdentifier
    lateinit var conferenceId: String

    @CommandHandler
    constructor(command: AddConferenceCommand) {
        // Validate the command

        // Generate an Event notifying the change produced by the Command

        val conferenceId = IdentifierFactory.getInstance().generateIdentifier()
        //This is equivalent in Axon to //val conferenceId = UUID.randomUUID().toString();;
        AggregateLifecycle.apply(ConferenceAddedEvent(
            conferenceId = conferenceId,
            name = command.name,
            website = command.website,
            location = command.location,
            family = command.family
        ))
    }

    @EventSourcingHandler
    fun handle(event : ConferenceAddedEvent) {
        this.conferenceId = event.conferenceId
    }

}