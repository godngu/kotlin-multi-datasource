package com.godngu.multidatasource.domain.primary

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
//@Rollback(false)
class MemberRepositoryTest {

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Test
    fun saveMember() {
        val savedMember = memberRepository.save(Member("memberA"))
        val foundMember = memberRepository.findById(savedMember.id)
            .orElseThrow(::RuntimeException)
        assertThat(foundMember.username).isEqualTo(savedMember.username)
    }
}
