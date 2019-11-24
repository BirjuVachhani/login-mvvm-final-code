package com.example.loginmvvm.login.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.observe
import com.example.loginmvvm.R
import com.example.loginmvvm.databinding.LoginActivityBinding
import com.example.loginmvvm.login.base.BaseActivity
import com.example.loginmvvm.login.state.LoginScreenState
import com.example.loginmvvm.login.viewmodel.LoginActivityVM
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<LoginActivityVM, LoginActivityBinding>(R.layout.activity_login) {

    override val viewModel: LoginActivityVM by viewModel()
    private val pref: SharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if ((pref.getString("token", "") ?: "").isNotBlank()) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        viewModel.state().observe(this) { state ->
            renderState(state)
        }

        etPassword.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                btnLogin.performClick()
                true
            } else false
        }
    }

    private fun renderState(state: LoginScreenState) = state.run {
        when (state) {
            is LoginScreenState.LoginSuccess -> {
                pref.edit().putString("token", state.response.token).apply()
                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
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
