package com.example.loginmvvm.login.di

import android.util.Log
import com.example.loginmvvm.login.model.Api
import com.example.loginmvvm.login.model.remote.ApiService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
 * Created by Birju Vachhani on 18 November 2019
 * Copyright © 2019 Login MVVM. All rights reserved.
 */

val networkModule = module {

    single {
        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.e("SERVER", message)
            }
        }).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().setPrettyPrinting().create()))
            .build()
            .create(ApiService::class.java)
    }

}