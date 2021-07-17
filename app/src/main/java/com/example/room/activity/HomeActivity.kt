package com.example.room.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.example.room.Adapter.PhotoGridAdapter
import com.example.room.R
import com.example.room.firebase.Auth
import com.example.room.firebase.Firebase
import kotlinx.android.synthetic.main.activity_home.*
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity(){

    var adapter: PhotoGridAdapter?=null
    val INSERTACT=111

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
    }

    override fun onStart() {
        super.onStart()
        init()
    }
    private fun setList(){
        adapter?.let { Firebase.readUrl(it) }

    }
    private fun init(){

        adapter= PhotoGridAdapter(this, ArrayList())
        Firebase.readUrl(adapter!!)
        recycler_list_photos.adapter=adapter
        recycler_list_photos.layoutManager= GridLayoutManager(this, GridLayoutManager.VERTICAL)


        button_add.setOnClickListener {
            startActivityForResult(Intent(this, InsertActivity::class.java),INSERTACT)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK){
            when(requestCode){
                INSERTACT->{
                    setList()
                }
            }
        }
    }
//    override fun onItemDelete(Target: Target) {



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            //R.id.menu_search->startActivity(Intent(this,CategoryActivity::class.java))
            R.id.menu_logout->{
                Auth.logOut(this)
            }
            android.R.id.home -> finish()       //set back button functionality
        }
        finish()
        return super.onOptionsItemSelected(item)
    }
}


