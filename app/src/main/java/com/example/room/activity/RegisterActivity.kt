package com.example.room.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.room.R
import com.example.room.firebase.Auth
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()
    }
    private fun init(){
        button_register.setOnClickListener {
            var email=edittext_email.text.toString()
            var password=edittext_password.text.toString()
            Auth.register(email,password,this)
        }
    }
}