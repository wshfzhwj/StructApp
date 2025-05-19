package com.saint.struct.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.saint.struct.databinding.ItemHomeProductBinding
import com.saint.struct.model.HomeItem
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator
import com.saint.struct.R
import com.saint.struct.tool.TAG

private const val TYPE_BANNER = 0
private const val TYPE_CONTENT = 1


// 修正PagingDataAdapter的继承参数
class HomeAdapter(private val onItemClick: (HomeItem) -> Unit) :
    PagingDataAdapter<HomeItem, RecyclerView.ViewHolder>(HomeItemDiffCallback) {

    // 添加DiffCallback实现
    object HomeItemDiffCallback : DiffUtil.ItemCallback<HomeItem>() {
        override fun areItemsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
            return oldItem == newItem
        }
    }

    private val bannerItems = listOf(
        "https://fastly.picsum.photos/id/662/375/200.jpg?hmac=NTKu5GoJnCBC_0-esaeG3CAaRRsyuGc8xMgjtDvGeC8",
        "https://fastly.picsum.photos/id/553/375/200.jpg?hmac=W_W2fS4O2RKH6gKjvmMFXutuMAVAxR2vFo2D1z4kzco",
        "https://fastly.picsum.photos/id/190/375/200.jpg?hmac=Cl6YxbEYeSH_C1ogIcp0TchXds58uMgDo27UwVlfOCE"
    )

    override fun getItemCount(): Int {
        return super.getItemCount() + 1 // 增加Banner占位
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_BANNER -> BannerViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_banner, parent, false)
            )

            else -> {
                return ProductViewHolder(
                    ItemHomeProductBinding.inflate(LayoutInflater.from(parent.context),parent,false),
                    onItemClick
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 && !snapshot().isEmpty()) TYPE_BANNER else TYPE_CONTENT
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BannerViewHolder -> holder.bind(bannerItems)
            is ProductViewHolder -> {
                // Handle empty state properly
                if (snapshot().isEmpty()) return
                val adjustedPosition = position - if (snapshot().items.isNotEmpty()) 1 else 0
                getItem(adjustedPosition)?.let { holder.bind(it) }
            }
        }
    }
}

class ProductViewHolder(
    private val binding: ItemHomeProductBinding,
    private val onItemClick: (HomeItem) -> Unit // 添加点击回调参数
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: HomeItem) {
        binding.apply {
            product = item
            Glide.with(itemView.context).load(item?.imageUrl).into(binding.ivProduct)

            root.setOnClickListener { onItemClick(item) } // 设置点击监听
            executePendingBindings()
        }
    }
}

class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val banner: Banner<String, SaintBannerImageAdapter> = view.findViewById(R.id.banner)

    fun bind(data: List<String>) {
        banner.apply {
            setAdapter(SaintBannerImageAdapter(data))
            // 绑定Fragment生命周期
            addBannerLifecycleObserver(
                (itemView.context as? FragmentActivity)
                    ?.supportFragmentManager
                    ?.findFragmentById(R.id.nav_host)
                    ?.viewLifecycleOwner ?: return
            )
            setBannerRound(8f)
            setLoopTime(3000)
            indicator = CircleIndicator(context)
        }
    }
}