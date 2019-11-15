package com.example.loginmvvm.login.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginmvvm.login.state.LoginScreenState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/*
 * Created by Birju Vachhani on 15 November 2019
 * Copyright © 2019 Login MVVM. All rights reserved.
 */

class MainActivityVM : ViewModel() {
    private val state = MutableLiveData<LoginScreenState>(LoginScreenState.Initial)

    fun state(): LiveData<LoginScreenState> = state

    fun login(email: String, password: String) = viewModelScope.launch {
        if (email.isBlank()) {
            state.postValue(LoginScreenState.EmailValidationError("Email cannot be empty"))
            return@launch
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            state.postValue(LoginScreenState.EmailValidationError("Invalid email address"))
            return@launch
        }

        if (password.isBlank()) {
            state.postValue(LoginScreenState.PasswordValidationError("Password cannot be empty"))
            return@launch
        } else if (password.length !in 8..20) {
            state.postValue(LoginScreenState.PasswordValidationError("Password must be 8 to 20 characters long!"))
            return@launch
        }
        state.postValue(LoginScreenState.Loading)
        delay(3000)
        if (email == "admin@gmail.com" && password == "12345678") {
            state.postValue(LoginScreenState.LoginSuccess)
        } else {
            state.postValue(LoginScreenState.LoginFailure("Invalid Credentials!"))
        }
    }
}