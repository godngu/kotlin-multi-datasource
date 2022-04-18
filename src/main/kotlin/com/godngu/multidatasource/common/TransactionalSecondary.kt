package com.godngu.multidatasource.common

import org.springframework.transaction.annotation.Transactional
import java.lang.annotation.Inherited
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.TYPE

@Target(TYPE, FUNCTION)
@Retention(RUNTIME)
@Inherited
@MustBeDocumented
// TODO: txManager 재설정 필요
@Transactional(transactionManager = "secondaryTransactionManager")
annotation class TransactionalSecondary(
    val readOnly: Boolean = false
)
