package com.godngu.multidatasource.domain.secondary

import com.godngu.multidatasource.common.TransactionalSecondary
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TeamService(
    val teamRepository: TeamRepository
) {

    @TransactionalSecondary
    fun saveTeam(teamName: String) {
        teamRepository.save(Team(teamName))
    }

    /**
     * Secondary 트랜잭션 매니저를 이용해서 readOnly 설정시 INSERT 쿼리가 발생하지 않는다.
     */
    @TransactionalSecondary(readOnly = true)
    fun saveTeamWhenReadOnly(teamName: String) {
        teamRepository.save(Team(teamName))
    }

    /**
     * Primary 트랜잭션 매니저를 이용해서 readOnly 설정을 하더라고 INSERT 쿼리가 발생한다.
     */
    @Transactional(readOnly = true)
    fun saveTeamWhenReadOnlyInPrimaryTransaction(teamName: String) {
        teamRepository.save(Team(teamName))
    }

    /**
     * 익셉션 발생시 트랜잭션이 rollback 된다.
     */
    @TransactionalSecondary
    fun saveTeamWhenException(teamName: String) {
        teamRepository.save(Team(teamName))
        throw RuntimeException("@TransactionalSecondary 내에서 익셉션 발생")
    }

    /**
     * Primary 트랜잭션에서 익셉션이 발생 하더라도 트랜잭션이 rollback 되지 않는다.
     */
    @Transactional
    fun saveTeamWhenExceptionInPrimaryTransaction(teamName: String) {
        teamRepository.save(Team(teamName))
        throw RuntimeException("@TransactionalSecondary 내에서 익셉션 발생")
    }
}
