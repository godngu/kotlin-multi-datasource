package com.godngu.multidatasource.domain.secondary

import org.springframework.data.jpa.repository.JpaRepository
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "team")
class Team(
    @Column(name = "team_name")
    val teamName: String
) {
    @Id
    @GeneratedValue
    @Column(name = "team_id")
    val id: Long = 0L
}

interface TeamRepository : JpaRepository<Team, Long>
