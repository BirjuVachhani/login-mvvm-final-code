package com.example.loginmvvm.login.model.remote

import com.example.loginmvvm.login.model.Api
import com.example.loginmvvm.login.model.remote.request.LoginRequest
import com.example.loginmvvm.login.model.remote.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

/*
 * Created by Birju Vachhani on 18 November 2019
 * Copyright © 2019 Login MVVM. All rights reserved.
 */

interface ApiService {

    @POST(Api.LOGIN_PATH)
    fun login(@Body request: LoginRequest): LoginResponse

}