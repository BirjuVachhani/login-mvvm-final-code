package com.example.loginmvvm.login.di

import com.example.loginmvvm.login.viewmodel.LoginActivityVM
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/*
 * Created by Birju Vachhani on 18 November 2019
 * Copyright © 2019 Login MVVM. All rights reserved.
 */

val vmModule = module {
    viewModel {
        LoginActivityVM()
    }
}