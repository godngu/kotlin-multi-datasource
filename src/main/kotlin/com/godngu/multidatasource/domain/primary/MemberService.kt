package com.godngu.multidatasource.domain.primary

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    val memberRepository: MemberRepository
) {

    @Transactional
    fun saveMember(username: String) {
        memberRepository.save(Member(username))
    }

    /**
     * 트랜잭션 매니저를 readOnly 설정시 INSERT 쿼리가 발생하지 않는다.
     */
    @Transactional(readOnly = true)
    fun saveMemberWhenReadOnly(username: String) {
        memberRepository.save(Member(username))
    }

    /**
     * 익셉션 발생시 트랜잭션이 rollback 된다.
     */
    @Transactional
    fun saveMemberWhenException(username: String) {
        memberRepository.save(Member(username))
        throw RuntimeException("@Transactional 내에서 익셉션 발생")
    }
}
