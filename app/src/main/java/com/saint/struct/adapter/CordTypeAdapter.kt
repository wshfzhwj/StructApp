package com.saint.struct.adapter

import android.content.Context
import android.graphics.Color
import com.saint.struct.R
import com.saint.struct.adapter.base.BaseViewHolder
import com.saint.struct.adapter.base.CommonBaseAdapter

class CordTypeAdapter(context: Context, datas: MutableList<String>, isOpenLoadMore: Boolean,
                      override val itemLayoutId: Int = R.layout.item_layout
) : CommonBaseAdapter<String>(context, datas, isOpenLoadMore) {
    private val c: IntArray

    init {
        c = intArrayOf(
            Color.parseColor("#33FF0000"),
            Color.parseColor("#3300FF00"),
            Color.parseColor("#330000FF")
        )
    }

    override fun convert(holder: BaseViewHolder, data: String, position: Int) {
        holder.setBgColor(R.id.item_tv, c[position % 3])
        holder.setText(R.id.item_tv, "item" + (position + 1))
    }

}
