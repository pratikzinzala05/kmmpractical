package com.kmmtest.di

import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

fun initKoinAndroid(
    appModules: List<Module> = emptyList(),
    appDeclaration: KoinAppDeclaration = {}
) {
    initKoin(
        appModules,
        appDeclaration,
    )
}