package com.godngu.multidatasource.domain.secondary

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface TeamRepository : JpaRepository<Team, Long> {

    fun findByTeamName(teamName: String): Optional<Team>
}
