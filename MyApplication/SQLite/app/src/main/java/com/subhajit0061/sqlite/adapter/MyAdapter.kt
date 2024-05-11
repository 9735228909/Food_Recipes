package com.subhajit0061.sqlite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.subhajit0061.sqlite.MainActivity
import com.subhajit0061.sqlite.OnClick
import com.subhajit0061.sqlite.R
import com.subhajit0061.sqlite.model.Contacts

class MyAdapter(private val list: ArrayList<Contacts>, private val onClick: OnClick) : Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : ViewHolder(itemView) {

        val txtName = itemView.findViewById<TextView>(R.id.txtName)
        val txtPhone = itemView.findViewById<TextView>(R.id.txtNumber)
        val imgDelete = itemView.findViewById<ImageView>(R.id.imgDelete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentContact = list[position]

        holder.txtName.text = currentContact.name
        holder.txtPhone.text = currentContact.phone

        holder.imgDelete.setOnClickListener {

            onClick.onDelete(position)

        }

    }

}