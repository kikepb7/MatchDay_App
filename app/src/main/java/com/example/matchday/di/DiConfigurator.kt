package com.example.matchday.di

import android.content.Context
import com.example.data.di.dataModule
import com.example.domain.di.domainModule
import com.example.ui.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appContext: Context, config: KoinAppDeclaration? = null) {
    startKoin {
        androidContext(appContext)
        config?.invoke(this)
        modules(
            dataModule,
            domainModule,
            uiModule
        )
    }
}