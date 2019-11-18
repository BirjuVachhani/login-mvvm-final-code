package com.example.loginmvvm.login.view

import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.loginmvvm.R
import org.koin.android.ext.android.inject

class HomeActivity : AppCompatActivity(R.layout.activity_home) {

    private val pref: SharedPreferences by inject()

    fun onLogoutButtonPressed(v: View) {
        pref.edit().remove("token").commit()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
