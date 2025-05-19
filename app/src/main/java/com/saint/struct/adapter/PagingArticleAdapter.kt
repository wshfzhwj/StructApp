package com.saint.struct.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.saint.struct.R
import com.saint.struct.bean.WanListBean

class PagingArticleAdapter : PagingDataAdapter<WanListBean, PagingArticleAdapter.ViewHolder>(itemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_page_recycle, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { bean ->
            with(holder) {
                desc.text = bean.desc
                date.text = bean.niceDate
                title.text = bean.title
                auth.text = bean.author
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val desc: TextView = itemView.findViewById(R.id.desc)
        val date: TextView = itemView.findViewById(R.id.date)
        val title: TextView = itemView.findViewById(R.id.title)
        val auth: TextView = itemView.findViewById(R.id.author)
    }

    companion object {
        private val itemCallback: DiffUtil.ItemCallback<WanListBean> = object : DiffUtil.ItemCallback<WanListBean>() {
            override fun areItemsTheSame(oldItem: WanListBean, newItem: WanListBean): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: WanListBean, newItem: WanListBean): Boolean {
                return oldItem == newItem
            }
        }
    }
}