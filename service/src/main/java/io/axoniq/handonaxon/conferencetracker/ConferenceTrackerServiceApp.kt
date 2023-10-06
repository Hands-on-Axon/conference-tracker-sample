package io.axoniq.handonaxon.conferencetracker

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class ConferenceTrackerServiceApp

fun main(args: Array<String>) {
    SpringApplication.run(ConferenceTrackerServiceApp::class.java, *args)
}