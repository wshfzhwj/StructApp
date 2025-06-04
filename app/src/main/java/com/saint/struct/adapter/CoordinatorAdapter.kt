package com.saint.struct.adapter

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
import com.saint.struct.model.HomeItem

class CoordinatorAdapter(private val onItemClick: (HomeItem) -> Unit) :
    PagingDataAdapter<HomeItem, CoordinatorAdapter.CordViewHolder>(CordItemDiffCallback) {
    // 添加DiffCallback实现
    object CordItemDiffCallback : DiffUtil.ItemCallback<HomeItem>() {
        override fun areItemsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
            return oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CordViewHolder {
        Log.e("CoordinatorAdapter", "onCreateViewHolder")
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cord_product, parent, false)
        return CordViewHolder(view)
    }

    override fun onBindViewHolder(holder: CordViewHolder, position: Int) {
        if (snapshot().isEmpty()) return
        getItem(position)?.let { bean ->
            with(holder) {
                Glide.with(itemView.context).load(bean.imageUrl).into(holder.product)
                title.text = bean.name
                price.text = bean.price.toString()
                root.setOnClickListener { onItemClick(bean) }
            }
        }
    }

    inner class CordViewHolder(
        var root: View,
    ) : RecyclerView.ViewHolder(root) {
        val product = root.findViewById<ImageView>(R.id.ivProduct)
        val title = root.findViewById<TextView>(R.id.tv_title)
        val price = root.findViewById<TextView>(R.id.tv_price)
    }
}



