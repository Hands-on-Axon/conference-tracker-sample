package io.axoniq.handonaxon.conferencetracker

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@SpringBootApplication
class ConferenceTrackerServiceApp {
    @ControllerAdvice
    class ErrorControllerAdvice {

        @ExceptionHandler(Throwable::class)
        fun handle(exception: Exception): ResponseEntity<String> {
            return ResponseEntity.internalServerError().body(exception.message)
        }
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(ConferenceTrackerServiceApp::class.java, *args)
}