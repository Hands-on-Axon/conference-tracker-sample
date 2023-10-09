package io.axoniq.handonaxon.conferencetracker.model

import io.axoniq.handonaxon.conferencetracker.api.*
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.common.IdentifierFactory
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.modelling.command.AggregateMember
import org.axonframework.modelling.command.ForwardMatchingInstances
import org.axonframework.spring.stereotype.Aggregate
import java.util.*

@Aggregate
class Conference() {

    @AggregateIdentifier
    lateinit var conferenceId: String
    @AggregateMember(eventForwardingMode = ForwardMatchingInstances::class)
    val editions = mutableMapOf<String, ConferenceEdition>()

    @CommandHandler
    constructor(command: AddConferenceCommand): this() {
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

    @CommandHandler
    fun newEdition(command: AddConferenceEditionCommand): String {
        //validate

        //trigger corresponding event
        val editionId = UUID.randomUUID().toString().split("-").first
        AggregateLifecycle.apply(ConferenceEditionAddedEvent(
            conferenceId = conferenceId,
            editionId = editionId,
            year = command.year,
            startDate = command.startDate,
            venue = command.venue
        ))

        return editionId;
    }


    @EventSourcingHandler
    fun handle(event: ConferenceAddedEvent) {
        this.conferenceId = event.conferenceId
    }

    @EventSourcingHandler
    fun handle(event: ConferenceEditionAddedEvent) {
        this.editions[event.editionId] = ConferenceEdition(event.editionId, event.startDate)
    }

}