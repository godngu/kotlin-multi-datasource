package com.godngu.multidatasource.domain.secondary

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
//@Rollback(false)
class TeamServiceTest {

    @Autowired
    lateinit var teamService: TeamService

    @Autowired
    lateinit var teamRepository: TeamRepository

    @Test
    fun saveTeam() {
        val teamName = "teamA"
        teamService.saveTeam(teamName)
        val team = teamRepository.findByTeamName(teamName)
            .orElseThrow(::RuntimeException)
        assertThat(team.teamName).isEqualTo(teamName)
    }

    @Test
    @DisplayName("커스텀 어노테이션의 트랜잭션 매니저 동작 확인 및 readOnly 확인 -> INSERT 쿼리 발생하지 않음")
    fun saveTeamWhenReadOnly() {
        val teamName = "teamB"
        teamService.saveTeamWhenReadOnly(teamName)
        val team = teamRepository.findByTeamName(teamName)
        assertThat(team).isEmpty
    }

    @Test
    @DisplayName("Primary 트랜잭션이 readOnly 설정 이더라도 적용되지 않고 INSERT 쿼리가 발생한다.")
    fun saveTeamWhenReadOnlyInPrimaryTransaction() {
        val teamName = "teamC"
        teamService.saveTeamWhenReadOnlyInPrimaryTransaction(teamName)
        val team = teamRepository.findByTeamName(teamName)
            .orElseThrow(::RuntimeException)
        assertThat(team.teamName).isEqualTo(teamName)
    }

    @Test
    @DisplayName("트랜잭션이 적용되어 rollback 된다.")
    fun saveTeamWhenException() {
        val teamName = "teamD"
        try {
            teamService.saveTeamWhenException(teamName)
        } catch (e: RuntimeException) {
            println(e.message)
        }
        val team = teamRepository.findByTeamName(teamName)
        assertThat(team).isEmpty
    }

    @Test
    @DisplayName("Primary 트랜잭션이 적용 되었다면 익셉션이 발생 하더라도 rollback 되지 않는다.")
    fun saveTeamWhenExceptionInPrimaryTransaction() {
        val teamName = "teamE"
        try {
            teamService.saveTeamWhenExceptionInPrimaryTransaction(teamName)
        } catch (e: RuntimeException) {
            println(e.message)
        }
        val team = teamRepository.findByTeamName(teamName)
            .orElseThrow(::RuntimeException)
        assertThat(team.teamName).isEqualTo(teamName)
    }
}
