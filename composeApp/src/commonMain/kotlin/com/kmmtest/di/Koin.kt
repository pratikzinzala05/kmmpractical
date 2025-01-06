package com.kmmtest.di

import com.kmmtest.platformModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration


fun initKoin(
    dependency: List<Module> = listOf(),
    appDeclaration: KoinAppDeclaration = {},
) {
    startKoin {
        appDeclaration()
        modules(
            listOf(
                platformModule,
            ) + dependency
        )
    }
}