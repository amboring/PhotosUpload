package com.example.room.firebase

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.example.room.Adapter.PhotoGridAdapter
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream


class Firebase {
    companion object{
        var firebaseDataBase=FirebaseDatabase.getInstance()
        var databaseReference=firebaseDataBase.getReference("photos")


        var storageRef = FirebaseStorage.getInstance().reference

        fun uploadPhoto(mAct: Activity, name:String, img:Bitmap){
            //create the reference of the image name and path
            val mountainImagesRef = storageRef.child("images/${name}.jpg")

            val baos = ByteArrayOutputStream()
            img.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()



           // var uploadTask = mountainsRef.putBytes(data)
            var uploadTask=mountainImagesRef.putBytes(data)
            uploadTask.addOnFailureListener {
                Log.i("****firebase","img upload failed")
            }.addOnSuccessListener { taskSnapshot ->
                if (taskSnapshot.getMetadata() != null) {
                    if (taskSnapshot?.getMetadata()?.getReference() != null) {
                        val result= taskSnapshot.getStorage().getDownloadUrl()
                        result.addOnSuccessListener(OnSuccessListener<Uri> { uri ->
                            val imageUrl = uri.toString()
                            Log.i("***getting url",imageUrl)
                            var imgId=databaseReference.push().key
                            if (imgId!= null) { databaseReference.child(imgId).setValue(imageUrl) }
                        })
                    }
                }


            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    mAct.finish()
                } else {
                }
            }

        }



        fun readUrl(adapter: PhotoGridAdapter) {
            val picListener = object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    adapter.mList=ArrayList<String>()


                    dataSnapshot.children.forEach {
                        adapter.mList.add(it.value.toString())
                        adapter.notifyDataSetChanged()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w(
                        "***firebase reading:",
                        "loadPost:onCancelled",
                        databaseError.toException()
                    )
                }
            }
            databaseReference.addValueEventListener(picListener)
        }
    }
}