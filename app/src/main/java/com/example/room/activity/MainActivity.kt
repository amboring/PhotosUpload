package com.example.room.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.room.R
import com.example.room.firebase.Auth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }
    private fun init(){
        if(Auth.auth.currentUser!=null){
            Log.i("******",Auth.auth.currentUser.toString())
            startActivity(Intent(this,HomeActivity::class.java))
        }


        button_register.setOnClickListener { startActivity(Intent(this,RegisterActivity::class.java)) }
        button_login.setOnClickListener { startActivity(Intent(this,LoginActivity::class.java)) }
        button_firebase.setOnClickListener {  startActivity(Intent(this,InsertActivity::class.java)) }

    }
}