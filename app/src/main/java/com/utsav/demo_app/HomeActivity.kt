package com.utsav.demo_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var btnAdd: Button
    lateinit var btnEdit: Button
    lateinit var btnDelete: Button
    lateinit var btnViewDetails: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
        initlist()
    }

    private fun initlist() {
        btnAdd.setOnClickListener(this)
        btnEdit.setOnClickListener(this)
        btnDelete.setOnClickListener(this)
        btnViewDetails.setOnClickListener(this)
    }

    private fun init() {
        btnAdd = findViewById(R.id.btn_add_homeActivity)
        btnEdit = findViewById(R.id.btn_edit_homeActivity)
        btnDelete = findViewById(R.id.btn_delete_homeActivity)
        btnViewDetails = findViewById(R.id.btn_view_homeActivity)
    }
    override fun onClick(v: View?) {
            when(v?.id){
                R.id.btn_add_homeActivity->{
                    val intent=Intent(applicationContext,MainActivity::class.java)
                    startActivity(intent)
                }
                R.id.btn_view_homeActivity->{
                    val intent =Intent(applicationContext,ViewUsers::class.java)
                    startActivity(intent)
                }
            }
    }
}
