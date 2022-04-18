package com.godngu.multidatasource.domain.primary

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface MemberRepository : JpaRepository<Member, Long> {

    fun findByUsername(username: String): Optional<Member>
}
