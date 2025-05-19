package com.saint.struct.adapter

import androidx.paging.PagingDataAdapter
import android.view.ViewGroup
import android.view.LayoutInflater
import com.saint.struct.R
import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saint.struct.bean.Project

class PagingProjectAdapter(val context: Context) : PagingDataAdapter<Project, PagingProjectAdapter.ViewHolder>(itemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_page_recycle, parent, false)
        return ViewHolder(view)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            with(holder) {
                holder.desc.text = it.desc
                holder.date.text = it.niceDate
                holder.title.text = it.title
                holder.auth.text = "作者：${it.author}"
                Glide.with(context).load(it.envelopePic).into(holder.image)
            }

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val desc: TextView = itemView.findViewById(R.id.desc)
        val date: TextView = itemView.findViewById(R.id.date)
        val title: TextView = itemView.findViewById(R.id.title)
        val auth: TextView = itemView.findViewById(R.id.author)
        val image: ImageView = itemView.findViewById(R.id.image)
    }

    companion object {
        private val itemCallback: DiffUtil.ItemCallback<Project> = object : DiffUtil.ItemCallback<Project>() {
            override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
                return oldItem == newItem
            }
        }
    }
}