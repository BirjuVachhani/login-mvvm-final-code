package com.example.loginmvvm.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.loginmvvm.DefaultDispatcherProvider
import com.example.loginmvvm.DispatcherProvider
import com.example.loginmvvm.login.base.BaseVM
import com.example.loginmvvm.login.model.remote.ApiService
import com.example.loginmvvm.login.model.remote.error.LoginError
import com.example.loginmvvm.login.model.remote.request.LoginRequest
import com.example.loginmvvm.login.state.LoginScreenState
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.koin.core.inject
import retrofit2.HttpException
import java.util.regex.Pattern

/*
 * Created by Birju Vachhani on 15 November 2019
 * Copyright © 2019 Login MVVM. All rights reserved.
 */

class LoginActivityVM(private val dispatchers: DispatcherProvider = DefaultDispatcherProvider()) :
    BaseVM() {

    private val emailPattern: Pattern by inject()
    private val apiService: ApiService by inject()
    val emailLiveData = MutableLiveData<String>("")
    val passwordLiveData = MutableLiveData<String>("")

    private val state = MutableLiveData<LoginScreenState>(LoginScreenState.Initial)

    fun state(): LiveData<LoginScreenState> = state

    fun login(email: String, password: String) = viewModelScope.launch(dispatchers.default()) {
        if (email.isBlank()) {
            state.postValue(LoginScreenState.EmailValidationError("Email cannot be empty"))
            return@launch
        } else if (!emailPattern.matcher(email).matches()) {
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
        runCatching {
            apiService.login(LoginRequest(email, password))
        }.fold({
            state.postValue(LoginScreenState.LoginSuccess(it))
        }, {
            val error = when (it) {
                is HttpException -> {
                    it.response()?.errorBody()?.string()?.let { error ->
                        Gson().fromJson(error, LoginError::class.java).error
                    } ?: "something went wrong"
                }
                else -> "Something went wrong!"
            }
            state.postValue(LoginScreenState.LoginFailure(error))
        })
    }
}