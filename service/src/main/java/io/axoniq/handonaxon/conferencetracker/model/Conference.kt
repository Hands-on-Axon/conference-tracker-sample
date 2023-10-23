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
import org.slf4j.LoggerFactory
import java.util.*

@Aggregate
class Conference() {
    @AggregateIdentifier
    lateinit var conferenceId: String
    @AggregateMember(eventForwardingMode = ForwardMatchingInstances::class)
    val editions = mutableMapOf<String, ConferenceEdition>()

    lateinit var ownerEmail: String

    @CommandHandler
    constructor(command: AddConferenceCommand): this() {
        logger.info("🔲 AddConferenceCommand received {}", command)
        // Validate the command

        // Generate an Event notifying the change produced by the Command

        val conferenceId = IdentifierFactory.getInstance().generateIdentifier()

        //This is equivalent in Axon to //val conferenceId = UUID.randomUUID().toString();;
        AggregateLifecycle.apply(ConferenceAddedEvent(
            conferenceId = conferenceId,
            name = command.name,
            website = command.website,
            location = command.location,
            family = command.family,
            ownerEmail = command.ownerEmail,
        ))
    }

    @CommandHandler
    fun newEdition(command: AddConferenceEditionCommand): String {
        logger.info("🔲 AddConferenceEditionCommand received {}", command)
        //validate
        if (!command.ownerEmail.equals(this.ownerEmail, true)) {
            throw IllegalArgumentException("User is not authorized to add edition to the conference "
                    + command.conferenceId + ", only the owner can do it")
        }

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
        logger.info("{} Processing ConferenceAddedEvent", getAggregatePhaseIcon())
        this.conferenceId = event.conferenceId
        this.ownerEmail = event.ownerEmail
    }

    private fun getAggregatePhaseIcon(): String {
        return if(AggregateLifecycle.isLive()) "📩" else "🏗️";
    }

    @EventSourcingHandler
    fun handle(event: ConferenceEditionAddedEvent) {
        logger.info("{} Processing ConferenceEditionAdded", getAggregatePhaseIcon())
        this.editions[event.editionId] = ConferenceEdition(event.editionId, event.startDate)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(Conference::class.java)
    }
}