package com.example.loginmvvm.login.state

import com.example.loginmvvm.login.model.remote.response.LoginResponse

/*
 * Created by Birju Vachhani on 15 November 2019
 * Copyright © 2019 Login MVVM. All rights reserved.
 */

sealed class LoginScreenState {
    object Initial : LoginScreenState()
    data class LoginSuccess(val response: LoginResponse) : LoginScreenState()
    data class LoginFailure(val error: String) : LoginScreenState()
    object Loading : LoginScreenState()
    data class EmailValidationError(val error: String) : LoginScreenState()
    data class PasswordValidationError(val error: String) : LoginScreenState()
}
