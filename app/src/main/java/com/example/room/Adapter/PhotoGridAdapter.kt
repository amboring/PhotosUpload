package com.example.room.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.room.R
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.photo_view.view.*

class PhotoGridAdapter (var mContext: Context, var mList: ArrayList<String>) : RecyclerView.Adapter<PhotoGridAdapter.ViewHolder>(){
    var index:Int?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =
            LayoutInflater.from(mContext).inflate(R.layout.photo_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        index=position
        var post = mList[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return mList.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(e: String) {

                var islandRef = com.example.room.firebase.Firebase.storageRef.child(e)

                val ONE_MEGABYTE: Long = 1024 * 1024

                Log.i("*** download",islandRef.downloadUrl.toString())
                Glide.with(mContext)
                    .load(e)
                    .into(itemView.image_view)

        }
    }

}