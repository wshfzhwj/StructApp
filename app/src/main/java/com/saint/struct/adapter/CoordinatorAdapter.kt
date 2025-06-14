package com.saint.struct.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saint.struct.R
import com.saint.struct.adapter.CoordinatorAdapter.CordViewHolder
import com.saint.struct.model.HomeItem

class CoordinatorAdapter(private val onItemClick: (HomeItem) -> Unit) :
    RecyclerView.Adapter<CordViewHolder>(){
    private var items: MutableList<HomeItem> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CordViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cord_product, parent, false)
        return CordViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(items: MutableList<HomeItem>){
        this.items = items
        notifyDataSetChanged()
    }

    fun clearData(){
        this.items.clear()
    }

    fun addAll(list: MutableList<HomeItem>){
        if (list.isEmpty()) {
            return
        }
        val lastIndex = this.items.size
        if (this.items.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size)
        }
    }


    override fun onBindViewHolder(holder: CordViewHolder, position: Int) {
        if (items.isEmpty()) return
        items[position].let { bean->
            with(holder){
                Glide.with(itemView.context).load(bean.imageUrl).into(holder.product)
                title.text = bean.name
                price.text = bean.price.toString()
                itemView.setOnClickListener { onItemClick(bean) }
            }
        }
    }

    override fun getItemCount(): Int {
        return if(items.isEmpty())  0 else items.size
    }

    inner class CordViewHolder(root : View) : RecyclerView.ViewHolder(root) {
        val product = itemView.findViewById<ImageView>(R.id.ivProduct)
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val price = itemView.findViewById<TextView>(R.id.tv_price)
    }
}



