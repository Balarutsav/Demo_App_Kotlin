package com.utsav.demo_app.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.utsav.demo_app.Database.UserDetail
import com.utsav.demo_app.R
import kotlinx.android.synthetic.main.raw_rv_user.view.*

class UserLIstAdapter (private val userlist : ArrayList<UserDetail>, private val context: Context) : RecyclerView.Adapter<UserLIstAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(LayoutInflater.from(context).inflate(R.layout.raw_rv_user,parent,false))
    }

    override fun getItemCount(): Int {
        return userlist.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
holder.tvDob.text=userlist.get(position).dateOfBirth
        holder.tvUserName.text=userlist.get(position).firstName+" "+userlist.get(position).lastName
        holder.tvMobileNumber.text=userlist.get(position).mobileNumber

    }
    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val tvUserName=view.tv_user_name
        val tvDob=view.tv_dob
        val tvMobileNumber=view.tv_mobile_number
        val cvRoot=view.cv_root
    }
}