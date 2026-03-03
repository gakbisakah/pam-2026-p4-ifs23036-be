package org.delcom.helpers

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.delcom.tables.MovieTable
import org.delcom.tables.PlantTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {
    val dbHost = environment.config.property("database.host").getString()
    val dbPort = environment.config.property("database.port").getString()
    val dbName = environment.config.property("database.name").getString()
    val dbUser = environment.config.property("database.user").getString()
    val dbPassword = environment.config.property("database.password").getString()

    val hikariConfig = HikariConfig().apply {
        driverClassName = "org.postgresql.Driver"
        jdbcUrl = "jdbc:postgresql://$dbHost:$dbPort/$dbName"
        username = dbUser
        password = dbPassword
        maximumPoolSize = 3
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    }

    val dataSource = HikariDataSource(hikariConfig)
    Database.connect(dataSource)

    transaction {
        SchemaUtils.create(PlantTable, MovieTable)
    }
}
