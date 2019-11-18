package com.example.loginmvvm

import android.app.Application
import com.example.loginmvvm.login.di.coreModule
import com.example.loginmvvm.login.di.networkModule
import com.example.loginmvvm.login.di.vmModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/*
 * Created by Birju Vachhani on 18 November 2019
 * Copyright © 2019 Login MVVM. All rights reserved.
 */

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(listOf(coreModule, vmModule, networkModule))
        }
    }
}