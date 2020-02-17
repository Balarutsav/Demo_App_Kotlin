package com.utsav.demo_app

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.utsav.demo_app.Database.DbHelper
import com.utsav.demo_app.Database.UserDetail
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var edt_fn: EditText
    lateinit var edt_ln: EditText
    lateinit var edt_dob: EditText
    lateinit var edt_mn: EditText
    lateinit var iv: ImageView
    lateinit var btn_submit: Button

    var FINAL_TAKE_PHOTO = 1
    var FINAL_CHOOSE_PHOTO = 2
    var imageuri: Uri? = null

    lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbHelper = DbHelper(this, null)
        dbHelper.updateById(1,"fname","lastname","20-10-2020","utav","img")
            /**get user list */
        var userlist: ArrayList<UserDetail> = dbHelper.getAllUser() as ArrayList<UserDetail>;
                Log.v("user list", userlist.size.toString())
        init()
        initlist()
    }

    private fun initlist() {
        btn_submit.setOnClickListener(this)
        iv.setOnClickListener(this)
    }

    private fun showalretdialog(): AlertDialog {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Select your choice")
        builder.setPositiveButton("Camera") { dialog: DialogInterface?, which: Int ->
            run {
                //                val outputImage = File(externalCacheDir, "output_image.jpg")
//                if(outputImage.exists()){
//                    outputImage.delete()
//                }
//                outputImage.createNewFile()
//                   Uri.fromFile(outputImage)


                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, FINAL_TAKE_PHOTO)
            }
        }
        builder.setNegativeButton("Gallery") { dialog, which ->
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, FINAL_CHOOSE_PHOTO)


        }


        val alertDialog: AlertDialog = builder.create()
        return alertDialog
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            FINAL_TAKE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    val cphoto = data!!.extras.get("data") as Bitmap
                    iv.setImageBitmap(cphoto)
                }
            FINAL_CHOOSE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(
                        application.contentResolver,
                        data?.data
                    )
                    // val cphoto = data!!.extras.get("data") as Bitmap
               var uri=     iv.setImageBitmap(bitmap)
                    Log.e("uri", uri.toString())
                }
        }
    }

    private fun init() {
        edt_fn = findViewById(R.id.edt_fn_activity_main)
        edt_ln = findViewById(R.id.edt_ln_activity_main)
        edt_dob = findViewById(R.id.edt_dob_activity_main)
        edt_mn = findViewById(R.id.edt_mn_activity_main)
        iv = findViewById(R.id.iv_activtymain_add)
        btn_submit = findViewById(R.id.btn_submit_activity_main)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_submit_activity_main -> {
                Toast.makeText(
                    applicationContext,
                    "You click submit",
                    Toast.LENGTH_SHORT
                ).show()
                val userDetai = UserDetail(
                    firstName = edt_fn.text.toString(),
                    lastName = edt_ln.text.toString(),
                    dateOfBirth = edt_dob.text.toString(),
                    mobileNumber = edt_mn.text.toString(),
                    image_path =     saveImageToInternalStorage(iv.drawable).toString()
                )
                dbHelper.addUser(userDetai)
                saveImageToInternalStorage(iv.drawable)
            }
            R.id.iv_activtymain_add -> showalretdialog().show()
        }
    }
    private fun saveImageToInternalStorage(drawableId: Drawable):Uri{
        // Get the image from drawable resource as drawable object

        // Get the bitmap from drawable object
        val bitmap = (drawableId as BitmapDrawable).bitmap

        // Get the context wrapper instance
        val wrapper = ContextWrapper(applicationContext)

        // Initializing a new file
        // The bellow line return a directory in internal storage
        var file = wrapper.getDir("images", Context.MODE_PRIVATE)


        // Create a file to save the image
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            // Get the file output stream
            val stream: OutputStream = FileOutputStream(file)

            // Compress bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            // Flush the stream
            stream.flush()

            // Close stream
            stream.close()
        } catch (e: IOException){ // Catch the exception
            e.printStackTrace()
        }

        // Return the saved image uri
        return Uri.parse(file.absolutePath)
    }
}



