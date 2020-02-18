package com.utsav.demo_app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utsav.demo_app.Adapter.UserLIstAdapter
import com.utsav.demo_app.Database.DbHelper
import com.utsav.demo_app.Database.UserDetail
import java.util.*

class ViewUsers : AppCompatActivity() {
    lateinit var rvUserList: RecyclerView
    lateinit var dbhelper: DbHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_user)
        init()
    }

    private fun init() {
        rvUserList=findViewById(R.id.rv_userlist)
        rvUserList.layoutManager = LinearLayoutManager(this)

        dbhelper= DbHelper(this,null)
        val userlist: ArrayList<UserDetail> = dbhelper.getAllUser() as ArrayList<UserDetail>
        Log.v("user list", userlist.size.toString())
        rvUserList.adapter = UserLIstAdapter(userlist,this)
        (rvUserList.adapter as UserLIstAdapter).notifyDataSetChanged()
    }
}
