package com.saint.struct.adapter.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saint.struct.adapter.base.interfaces.OnItemChildClickListener
import com.saint.struct.adapter.base.interfaces.OnMultiItemClickListeners

/**
 * Author: Othershe
 * Time: 2016/9/9 16:21
 */
abstract class MultiBaseAdapter<T>(
    context: Context,
    datas: MutableList<T>,
    isOpenLoadMore: Boolean
) : BaseAdapter<T>(context, datas, isOpenLoadMore) {
    private var mItemClickListener: OnMultiItemClickListeners<T?>? = null

    private val mItemChildIds = ArrayList<Int?>()
    private val mItemChildListeners = ArrayList<OnItemChildClickListener<T?>?>()

    protected abstract fun convert(holder: BaseViewHolder?, data: T?, position: Int, viewType: Int)

    protected abstract fun getItemLayoutId(viewType: Int): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (isCommonItemView(viewType)) {
            return BaseViewHolder.Companion.create(mContext, getItemLayoutId(viewType), parent)
        }
        return super.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType: Int = holder.itemViewType
        if (isCommonItemView(viewType)) {
            bindCommonItem(holder, position, viewType)
        }
    }

    private fun bindCommonItem(holder: RecyclerView.ViewHolder, position: Int, viewType: Int) {
        val baseViewHolder = holder as BaseViewHolder
        convert(baseViewHolder, mDatas.get(position), position, viewType)

        baseViewHolder.convertView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if (mItemClickListener != null) {
                    mItemClickListener!!.onItemClick(
                        baseViewHolder,
                        mDatas.get(position),
                        position,
                        viewType
                    )
                }
            }
        })

        for (i in mItemChildIds.indices) {
            val tempI = i
            if (baseViewHolder.convertView.findViewById<View?>(mItemChildIds.get(i)!!) != null) {
                baseViewHolder.convertView.findViewById<View?>(mItemChildIds.get(i)!!)
                    .setOnClickListener(object : View.OnClickListener {
                        override fun onClick(v: View?) {
                            mItemChildListeners.get(tempI)!!
                                .onItemChildClick(baseViewHolder, mDatas.get(position), position)
                        }
                    })
            }
        }
    }

    fun setOnMultiItemClickListener(itemClickListener: OnMultiItemClickListeners<T?>?) {
        mItemClickListener = itemClickListener
    }

    fun setOnItemChildClickListener(
        viewId: Int,
        itemChildClickListener: OnItemChildClickListener<T?>?
    ) {
        mItemChildIds.add(viewId)
        mItemChildListeners.add(itemChildClickListener)
    }
}
