package com.saint.struct.adapter

import androidx.paging.PagingDataAdapter
import android.view.ViewGroup
import android.view.LayoutInflater
import com.saint.struct.R
import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.saint.struct.bean.Project

class PagingKtAdapter : PagingDataAdapter<Project, PagingKtAdapter.ViewHolder>(itemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_page_recycle, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bean = getItem(position)
        if (bean != null) {
            holder.desc.text = bean.desc
            holder.date.text = bean.niceDate
            holder.title.text = bean.title
            holder.auth.text = bean.author
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var desc: TextView
        var title: TextView
        var date: TextView
        var auth: TextView

        init {
            title = itemView.findViewById(R.id.title)
            desc = itemView.findViewById(R.id.desc)
            date = itemView.findViewById(R.id.date)
            auth = itemView.findViewById(R.id.author)
        }
    }

    companion object {
        private val itemCallback: DiffUtil.ItemCallback<Project> = object : DiffUtil.ItemCallback<Project>() {
            override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
                return oldItem.niceDate == newItem.niceDate
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
                return oldItem == newItem
            }
        }
    }
}