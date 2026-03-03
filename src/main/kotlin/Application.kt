package org.delcom

import io.github.cdimascio.dotenv.dotenv
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import kotlinx.serialization.json.Json
import org.delcom.module.appModule
import org.delcom.helpers.configureDatabases
import org.koin.ktor.plugin.Koin

fun main(args: Array<String>) {
    val dotenv = try {
        dotenv {
            directory = "."
            ignoreIfMissing = true
        }
    } catch (e: Exception) {
        null
    }

    dotenv?.entries()?.forEach {
        System.setProperty(it.key, it.value)
    }

    EngineMain.main(args)
}

fun Application.module() {
    install(CORS) {
        anyHost()
        allowHeader(io.ktor.http.HttpHeaders.ContentType)
        allowMethod(io.ktor.http.HttpMethod.Options)
        allowMethod(io.ktor.http.HttpMethod.Put)
        allowMethod(io.ktor.http.HttpMethod.Patch)
        allowMethod(io.ktor.http.HttpMethod.Delete)
    }

    install(ContentNegotiation) {
        json(
            Json {
                explicitNulls = false
                prettyPrint = true
                ignoreUnknownKeys = true
            }
        )
    }

    install(Koin) {
        modules(appModule)
    }

    configureDatabases()
    configureRouting()
}
