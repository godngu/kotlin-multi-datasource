package com.godngu.multidatasource.domain.secondary

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
//@Rollback(false)
class TeamRepositoryTest {

    @Autowired
    lateinit var teamRepository: TeamRepository

    @Test
    fun saveTeam() {
        val savedTeam = teamRepository.save(Team("teamA"))
        val foundTeam = teamRepository.findById(savedTeam.id).orElseThrow(::RuntimeException)
        assertThat(foundTeam.teamName).isEqualTo(savedTeam.teamName)
    }
}
