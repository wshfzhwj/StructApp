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

class Paging3KotlinAdapter(val context:Context) : PagingDataAdapter<Project, Paging3KotlinAdapter.ViewHolder>(itemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_page_recycle, parent, false)
        return ViewHolder(view)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bean = getItem(position)
        bean?.let {
            holder.desc.text = it.desc
            holder.date.text = it.niceDate
            holder.title.text = it.title
            holder.auth.text = "作者：${it.author}"
//            Glide.with(holder.image).load(it.envelopePic)
            Glide.with(context).load(bean.envelopePic).into(holder.image)
        }
//        if (bean != null) {
//            holder.desc.text = bean.desc
//            holder.date.text = bean.niceDate
//            holder.title.text = bean.title
//            holder.auth.text = "作者：${bean.author}"
//            bean.envelopePic?.let {
//                Glide.with(context).load(bean.envelopePic).into(holder.image)
//            }
//        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var desc: TextView
        var title: TextView
        var date: TextView
        var auth: TextView
        var image: ImageView

        init {
            title = itemView.findViewById(R.id.title)
            desc = itemView.findViewById(R.id.desc)
            date = itemView.findViewById(R.id.date)
            auth = itemView.findViewById(R.id.author)
            image = itemView.findViewById(R.id.image)
        }
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