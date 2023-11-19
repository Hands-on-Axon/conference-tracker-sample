package io.axoniq.handonaxon.conferencetracker.projections

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Entity
@Table(name="conference")
data class Conference(
    @Id @Column(name = "conference_id")
    val conferenceId : String,
    val name: String,
    val website: String
)

@Repository
interface ConferenceRepository: CrudRepository<Conference, String> {
    fun findByName(name: String): Conference?
}