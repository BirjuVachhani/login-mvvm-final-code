package com.example.loginmvvm.login.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.loginmvvm.R

class HomeActivity : AppCompatActivity(R.layout.activity_home) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun onLogoutButtonPressed(v: View) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
