package com.example.room.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.room.R
import com.example.room.firebase.Auth
import kotlinx.android.synthetic.main.activity_login.*
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }
    private fun init(){
        button_login.setOnClickListener {
            var email=edittext_email.text.toString()
            var password=edittext_password.text.toString()
            Auth.login(email,password,this)
        }
    }
}