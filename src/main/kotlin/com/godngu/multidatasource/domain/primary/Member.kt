package com.godngu.multidatasource.domain.primary

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "member")
class Member(
    @Column(name = "username")
    val username: String
) {
    @Id
    @GeneratedValue
    @Column(name = "member_no")
    val id: Long = 0L
}
