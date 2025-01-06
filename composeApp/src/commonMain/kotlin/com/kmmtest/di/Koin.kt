package com.kmmtest.di

import com.kmmtest.network.UserDataSource
import com.kmmtest.network.UserRepo
import com.kmmtest.platformModule
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.cio.ChannelIOException
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


fun initKoin(
    dependency: List<Module> = listOf(),
    appDeclaration: KoinAppDeclaration = {},
) {
    startKoin {
        appDeclaration()
        modules(
            listOf(
                platformModule,httpClientModule,userRepoModule
            ) + dependency
        )
    }
}

val httpClientModule = module {
    single {

        HttpClient {


            install(DefaultRequest) {
                apply {
                    headers.apply {
                        append("Content-Type", "application/json")
                    }
                }
            }

            install(Logging) {
                level =LogLevel.ALL

                logger = object : Logger {
                    override fun log(message: String) {
                        println("http $message")
                    }
                }

            }

            install(HttpTimeout) {
                requestTimeoutMillis = 10000
                connectTimeoutMillis = 5000
                socketTimeoutMillis = 10000
            }

            install(ContentNegotiation) {
                json(
                    json = Json { ignoreUnknownKeys = true },
                    contentType = ContentType.Application.Json
                )
            }


        }


    }
}

val userRepoModule = module {
    single<UserRepo> { UserDataSource(get()) }
}