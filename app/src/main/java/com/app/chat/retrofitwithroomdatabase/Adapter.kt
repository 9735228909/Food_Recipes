package com.app.chat.retrofitwithroomdatabase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(val productlist : ArrayList<Product>):RecyclerView.Adapter<Adapter.Myviewholder>() {
    class Myviewholder(itemview: View):RecyclerView.ViewHolder(itemview){
        val title: TextView = itemview.findViewById(R.id.title_text)
        val description: TextView = itemview.findViewById(R.id.description_text)
        val price: TextView = itemview.findViewById(R.id.price_text)

        fun bund(product: Product){
            title.text = product.title
            description.text = product.description
            price.text = product.price.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return Myviewholder(view)
    }

    override fun getItemCount(): Int {
        return productlist.size
    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {
        holder.bund(productlist[position])
    }
}