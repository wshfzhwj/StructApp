package com.saint.struct.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saint.struct.R
import com.youth.banner.adapter.BannerAdapter

class SaintBannerImageAdapter(private val imageUrls: List<String>) :
    BannerAdapter<String, SaintBannerImageAdapter.ImageHolder>(imageUrls) {

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): ImageHolder {
        val view = LayoutInflater.from(parent?.context)
            .inflate(R.layout.item_banner_image, parent, false)
        return ImageHolder(view)
    }

    override fun onBindView(holder: ImageHolder?, data: String?, position: Int, size: Int) {
        holder?.imageView?.let { iv ->
            data?.takeIf { it.isNotEmpty() }?.let { 
                Glide.with(iv.context)
                    .load(it)
                    .into(iv)
            } ?: iv.setImageResource(R.drawable.placeholder) // 添加空数据兜底处理
        }
    }

    inner class ImageHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.iv_banner)
    }
}