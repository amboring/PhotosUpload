package com.example.room.firebase

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.room.activity.HomeActivity
import com.example.room.activity.LoginActivity
import com.example.room.activity.MainActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class Auth {
    companion object{

        var auth: FirebaseAuth = FirebaseAuth.getInstance()

        fun register(email:String,password:String,mAct: Activity){


            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(mAct,object: OnCompleteListener<AuthResult> {
                    override fun onComplete(task: Task<AuthResult>) {
                        if(task.isSuccessful){
                            mAct.startActivity(Intent(mAct.applicationContext, LoginActivity::class.java))
                            mAct.finish()
                        }else{ Toast.makeText(mAct.applicationContext,"register failed", Toast.LENGTH_SHORT).show() }
                    }
                })
        }

        fun login(email:String,password:String,mAct: Activity){
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(mAct,object:OnCompleteListener<AuthResult>{
                    override fun onComplete(p0: Task<AuthResult>) {
                        if(p0.isSuccessful){
                            mAct.startActivity(Intent(mAct.applicationContext,HomeActivity::class.java))
                            mAct.finish()
                        }else{Toast.makeText(mAct.applicationContext,"login failed", Toast.LENGTH_SHORT).show() }
                    }

                })
        }

        fun logOut(mAct: Activity){
                auth.signOut()
                mAct.startActivity(Intent(mAct,MainActivity::class.java))
                mAct.finish()
        }
    }

}