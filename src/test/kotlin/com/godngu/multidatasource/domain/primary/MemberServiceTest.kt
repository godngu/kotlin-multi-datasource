package com.godngu.multidatasource.domain.primary

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback

@SpringBootTest
//@Rollback(false)
class MemberServiceTest {

    @Autowired
    lateinit var memberService: MemberService

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Test
    fun saveMember() {
        val username = "memberA"
        memberService.saveMember(username)
        val member = memberRepository.findByUsername(username)
            .orElseThrow(::RuntimeException)
        assertThat(member.username).isEqualTo(username)
    }

    /**
     * Primary DB Read Replica 테스트
     */
    @Test
    @DisplayName("@Transactional(readOnly=true) 일 경우 INSERT 쿼리가 나가지 않으므로 find 시 empty 리턴 된다.")
    fun saveMemberWhenReadOnly() {
        val username = "memberB"
        memberService.saveMemberWhenReadOnly(username)
        val member = memberRepository.findByUsername(username)
        assertThat(member).isEmpty
    }

    @Test
    @DisplayName("익셉션 발생시 트랜잭션이 rollback 되어 find 시 결과가 empty 이다.")
    fun saveMemberWhenException() {
        val username = "memberC"
        try {
            memberService.saveMemberWhenException(username)
        } catch (e: RuntimeException) {
            println(e.message)
        }
        val member = memberRepository.findByUsername(username)
        assertThat(member).isEmpty
    }
}
