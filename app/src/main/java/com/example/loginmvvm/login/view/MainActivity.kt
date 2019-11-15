package com.example.loginmvvm.login.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.example.loginmvvm.R
import com.example.loginmvvm.login.state.LoginScreenState
import com.example.loginmvvm.login.viewmodel.MainActivityVM
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel: MainActivityVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.state().observe(this) { state ->
            renderState(state)
        }

        etPassword.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onLoginButtonPressed(v)
                true
            } else false
        }
    }

    fun onLoginButtonPressed(v: View) {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        viewModel.login(email, password)
    }

    private fun renderState(state: LoginScreenState) = state.run {
        when (state) {
            is LoginScreenState.LoginSuccess -> {
                startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                finish()
            }
            is LoginScreenState.LoginFailure -> {
                enableViews(true)
                Snackbar.make(root, state.error, Snackbar.LENGTH_SHORT).show()
            }
            is LoginScreenState.EmailValidationError -> {
                enableViews(true)
                tilEmail.error = state.error
            }
            is LoginScreenState.PasswordValidationError -> {
                enableViews(true)
                tilEmail.error = ""
                tilPassword.error = state.error
            }
            LoginScreenState.Loading -> {
                tilEmail.error = ""
                tilPassword.error = ""
                enableViews(false)
            }
        }
    }

    private fun enableViews(enable: Boolean) {
        btnLogin.isEnabled = enable
        etEmail.isEnabled = enable
        etPassword.isEnabled = enable
        progressBar.visibility = if (enable) View.INVISIBLE else View.VISIBLE
    }
}
