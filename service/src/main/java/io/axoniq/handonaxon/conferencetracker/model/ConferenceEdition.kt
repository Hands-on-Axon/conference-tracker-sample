package io.axoniq.handonaxon.conferencetracker.model

import io.axoniq.handonaxon.conferencetracker.api.ConferenceEditionScheduledEvent
import io.axoniq.handonaxon.conferencetracker.api.ScheduleConferenceEditionCommand
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.modelling.command.EntityId
import java.time.LocalDate


class ConferenceEdition(
    @EntityId
    val editionId: String,
    var startDate: LocalDate?
) {

    @CommandHandler
    fun handle(cmd: ScheduleConferenceEditionCommand) {
        //validate

        //trigger the Event
        if(cmd.startDate != startDate) {
            AggregateLifecycle.apply(ConferenceEditionScheduledEvent(cmd.conferenceId, cmd.editionId, cmd.startDate))
        }
    }

    @EventSourcingHandler
    fun on(event: ConferenceEditionScheduledEvent) {
        startDate = event.startDate
    }
}