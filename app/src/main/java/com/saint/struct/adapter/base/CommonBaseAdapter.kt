package com.saint.struct.adapter.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saint.struct.adapter.base.interfaces.OnItemChildClickListener
import com.saint.struct.adapter.base.interfaces.OnItemClickListener
import com.saint.struct.adapter.base.interfaces.OnSwipeMenuClickListener

abstract class CommonBaseAdapter<T>(
    val context: Context,
    datas: MutableList<T>,
    isOpenLoadMore: Boolean
) : BaseAdapter<T>(context, datas, isOpenLoadMore) {
    private lateinit var mItemClickListener: OnItemClickListener<T>


    private val mViewId = ArrayList<Int>()
    private val mListener = ArrayList<OnSwipeMenuClickListener<T>>()

    private val mItemChildIds = ArrayList<Int?>()
    private val mItemChildListeners = ArrayList<OnItemChildClickListener<T?>?>()

    protected abstract fun convert(holder: BaseViewHolder, data: T, position: Int)

    protected abstract val itemLayoutId: Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (isCommonItemView(viewType)) {
            return BaseViewHolder.Companion.create(
                context,
                this.itemLayoutId, parent
            )
        }
        return super.onCreateViewHolder(parent, viewType)
    }

     override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType: Int = holder.itemViewType
        if (isCommonItemView(viewType)) {
            bindCommonItem(holder, position)
        }
    }

    private fun bindCommonItem(holder: RecyclerView.ViewHolder?, position: Int) {
        val baseViewHolder = holder as BaseViewHolder
        convert(baseViewHolder, mDatas.get(position), position)

        baseViewHolder.convertView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                    mItemClickListener.onItemClick(baseViewHolder, mDatas.get(position), position)
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

        if (mViewId.size > 0 && mListener.size > 0 && baseViewHolder.swipeView != null) {
            val swipeView: ViewGroup = baseViewHolder.swipeView as ViewGroup

            for (i in mViewId.indices) {
                val tempI = i
                swipeView.findViewById<View>(mViewId.get(i))
                    .setOnClickListener(object : View.OnClickListener {
                        override fun onClick(v: View?) {
                            mListener.get(tempI)!!
                                .onSwipeMenuClick(baseViewHolder, mDatas.get(position), position)
                        }
                    })
            }
        }
    }

    override fun getViewType(position: Int, data: T): Int {
        return BaseAdapter.Companion.TYPE_COMMON_VIEW
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener<T>) {
        mItemClickListener = itemClickListener
    }

    fun setOnSwipeMenuClickListener(
        viewId: Int,
        swipeMenuClickListener: OnSwipeMenuClickListener<T>
    ) {
        mViewId.add(viewId)
        mListener.add(swipeMenuClickListener)
    }

    fun setOnItemChildClickListener(
        viewId: Int,
        itemChildClickListener: OnItemChildClickListener<T?>?
    ) {
        mItemChildIds.add(viewId)
        mItemChildListeners.add(itemChildClickListener)
    }
}
