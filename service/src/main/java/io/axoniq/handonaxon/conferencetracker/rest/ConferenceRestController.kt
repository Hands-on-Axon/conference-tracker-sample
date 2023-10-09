package io.axoniq.handonaxon.conferencetracker.rest

import io.axoniq.handonaxon.conferencetracker.api.AddConferenceCommand
import io.axoniq.handonaxon.conferencetracker.api.AddConferenceEditionCommand
import io.axoniq.handonaxon.conferencetracker.api.AllConferencesQuery
import io.axoniq.handonaxon.conferencetracker.api.ScheduleConferenceEditionCommand
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.responsetypes.ResponseType
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

@RestController()
class ConferenceRestController(
    private val commandGateway: CommandGateway,
    private val queryGateway: QueryGateway,
) {
    @PostMapping("/conference")
    fun createConference(@RequestBody cmd: AddConferenceCommand): CompletableFuture<String> {
        return commandGateway.send(cmd)
    }

    @PostMapping("/conference/edition")
    fun defineEdition(@RequestBody cmd: AddConferenceEditionCommand): CompletableFuture<String> {
        return commandGateway.send(cmd);
    }

    @PostMapping("/conference/edition/schedule")
    fun scheduleEdition(@RequestBody cmd: ScheduleConferenceEditionCommand): CompletableFuture<String> {
        return commandGateway.send(cmd);
    }

    @GetMapping("/conference")
    fun listConferences(): List<String> {
        return queryGateway.query(AllConferencesQuery(), ResponseTypes.multipleInstancesOf(String::class.java)).get()
    }
}