package com.example.room.activity

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.room.R
import com.example.room.firebase.Firebase
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_insert.*
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.insert_main_content.*
import java.time.LocalDateTime
import java.util.*


class InsertActivity : AppCompatActivity() {
    val REQUEST_GALLERY_IMAGE=51993
    val REQUEST_CAMERA_IMAGE=419451

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)
        init()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun init(){
        button_camera.setOnClickListener { openCamara() }
        button_gallery.setOnClickListener { openGallery() }
        button_upload.setOnClickListener { uploadImage() }
    }
    private fun openCamara(){
        Dexter.withContext(this)
        .withPermissions(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)
        .withListener(object: MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                //check if all granted
                if(report!!.areAllPermissionsGranted()){
                        var intent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(intent,REQUEST_CAMERA_IMAGE)
                    Toast.makeText(applicationContext,"all permissions granted", Toast.LENGTH_SHORT).show()
                }
                if (report!!.isAnyPermissionPermanentlyDenied){
                    //Toast.makeText(applicationContext,"permission denied permanently",Toast.LENGTH_SHORT).show()
                    showDialogue()
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<PermissionRequest>?,
                token: PermissionToken?
            ) {
                token?.continuePermissionRequest()
            }
        }).onSameThread().check()
    }

    private fun openGallery(){
        Dexter.withContext(this)
        .withPermissions(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        .withListener(object: MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                //check if all granted
                if(report!!.areAllPermissionsGranted()){
                    var intent= Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(intent,REQUEST_GALLERY_IMAGE)
                    Toast.makeText(applicationContext,"all permissions granted", Toast.LENGTH_SHORT).show()
                }
                if (report!!.isAnyPermissionPermanentlyDenied){
                    showDialogue()
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<PermissionRequest>?,
                token: PermissionToken?
            ) {
                token?.continuePermissionRequest()
            }
        }).onSameThread().check()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== RESULT_OK){
            when (requestCode){
                REQUEST_GALLERY_IMAGE->{}
                REQUEST_CAMERA_IMAGE->{
                        val imageBitmap = data?.extras?.get("data") as Bitmap
                        image_view_toupload.setImageBitmap(imageBitmap)
                    }
                }
        }
     }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun uploadImage(){

       // val bitmap = (image_view_toupload.drawable as BitmapDrawable).bitmap


        val upLoadingImage = (image_view_toupload.getDrawable() as BitmapDrawable).bitmap
        if (upLoadingImage!=null){
            Log.i("***uploading",upLoadingImage.toString())
            Firebase.uploadPhoto(this,LocalDateTime.now().toString(),upLoadingImage)
            //Firebase.uploadImg(LocalDateTime.now().toString(),image_view_toupload.getDrawable())
        }

    }
    private fun showDialogue(){
        var builder= AlertDialog.Builder(this)
        builder.setTitle("Need permission to store picture")
        builder.setMessage("Please give the permission in the seting to this app")
        builder.setPositiveButton("Go to setting", object:DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog?.dismiss()
                openAppSettings()
            }
        })
        builder.setNegativeButton("Cancel",object :DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which:Int) {
                dialog?.dismiss()
            }
        })
        builder.show()
    }

    fun openAppSettings(){
        var intent= Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        var uri= Uri.fromParts("package",packageName,null)
        intent.setData(uri)
        startActivityForResult(intent,REQUEST_CAMERA_IMAGE)
    }

}