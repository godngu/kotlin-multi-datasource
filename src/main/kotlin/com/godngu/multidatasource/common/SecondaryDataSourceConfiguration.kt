package com.godngu.multidatasource.common

import com.godngu.multidatasource.common.SecondaryDataSourceConfiguration.DataSourceType.READER
import com.godngu.multidatasource.common.SecondaryDataSourceConfiguration.DataSourceType.WRITER
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.transaction.support.TransactionSynchronizationManager
import javax.sql.DataSource

@Configuration
@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration::class])
class SecondaryDataSourceConfiguration {

    enum class DataSourceType {
        WRITER,
        READER
    }

    internal class RoutingDataSource : AbstractRoutingDataSource() {
        override fun determineCurrentLookupKey(): Any = when {
            TransactionSynchronizationManager.isCurrentTransactionReadOnly() -> READER
            else -> WRITER
        }
    }

    @Bean(name = ["writerSecondaryDataSource"])
    @ConfigurationProperties(prefix = "spring.datasource.secondary.writer")
    fun writerSecondaryDataSource(): DataSource = DataSourceBuilder.create()
        .type(HikariDataSource::class.java)
        .build()

    @Bean(name = ["readerSecondaryDataSource"])
    @ConfigurationProperties(prefix = "spring.datasource.secondary.reader")
    fun readerSecondaryDataSource(): DataSource = DataSourceBuilder.create()
        .type(HikariDataSource::class.java)
        .build()
        .apply { isReadOnly = true }

    @Bean(name = ["routingSecondaryDataSource"])
    fun routingDataSource(
        @Qualifier("writerSecondaryDataSource") writerSecondaryDataSource: DataSource,
        @Qualifier("readerSecondaryDataSource") readerSecondaryDataSource: DataSource,
    ): DataSource {
        val routingDataSource = RoutingDataSource()
        val dataSources: Map<Any, Any> = mapOf(WRITER to writerSecondaryDataSource, READER to readerSecondaryDataSource)
        routingDataSource.setTargetDataSources(dataSources)
        routingDataSource.setDefaultTargetDataSource(writerSecondaryDataSource)
        return routingDataSource
    }

    // Writer/Reader 모두 @Primary 어노테이션을 붙여준다.
    @Primary
    @Bean
    fun secondaryDataSource(@Qualifier("routingSecondaryDataSource") routingDataSource: DataSource) =
        LazyConnectionDataSourceProxy(routingDataSource)
}
