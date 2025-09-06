package com.saint.struct.adapter.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.saint.struct.adapter.base.interfaces.OnLoadMoreListener

abstract class BaseAdapter<T>(
    protected var mContext: Context, var mDatas: MutableList<T>, //是否开启加载更多
    private val mOpenLoadMore: Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mLoadMoreListener: OnLoadMoreListener? = null

    private var isAutoLoadMore = true //是否自动加载，当数据不满一屏幕会自动加载

    private var isRemoveEmptyView = false

    private lateinit var mLoadingView: View //分页加载中view
    private lateinit var mLoadFailedView: View //分页加载失败view
    private lateinit var mLoadEndView: View //分页加载结束view
    private var mEmptyView: View? = null//首次预加载view
    private var mReloadView: View? = null //首次预加载失败、或无数据的view
    private lateinit var mFooterLayout: RelativeLayout //footer view

    private var isReset = false //开始重新加载数据

    protected abstract fun getViewType(position: Int, data: T): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        var baseViewHolder: BaseViewHolder
        when (viewType) {
            TYPE_FOOTER_VIEW -> {
                mFooterLayout = RelativeLayout(mContext)
                baseViewHolder = BaseViewHolder.Companion.create(mFooterLayout)
            }

            TYPE_EMPTY_VIEW -> baseViewHolder = BaseViewHolder.Companion.create(mEmptyView!!)
            TYPE_RELOAD_VIEW -> baseViewHolder = BaseViewHolder.Companion.create(mReloadView!!)
            else -> baseViewHolder = BaseViewHolder.Companion.create(View(mContext))

        }
        return baseViewHolder
    }

    override fun getItemCount(): Int {
        if (mDatas.isEmpty() && mEmptyView == null) {
            return 1
        }
        return mDatas.size + this.footerViewCount
    }

    override fun getItemViewType(position: Int): Int {
        if (mDatas.isEmpty()) {
            if (mEmptyView != null && !isRemoveEmptyView) {
                return TYPE_EMPTY_VIEW
            }

            if (isRemoveEmptyView && mReloadView != null) {
                return TYPE_RELOAD_VIEW
            } else {
                return TYPE_NODATE_VIEW
            }
        }

        if (isFooterView(position)) {
            return TYPE_FOOTER_VIEW
        }

        return getViewType(position, mDatas.get(position))
    }

    /**
     * 根据positiond得到data
     *
     * @param position
     * @return
     */
    fun getItem(position: Int): T? {
        if (mDatas.isEmpty()) {
            return null
        }
        return mDatas[position]
    }

    /**
     * 是否是FooterView
     *
     * @param position
     * @return
     */
    private fun isFooterView(position: Int): Boolean {
        return mOpenLoadMore && position >= this.itemCount - 1
    }

    protected fun isCommonItemView(viewType: Int): Boolean {
        return viewType != TYPE_EMPTY_VIEW && viewType != TYPE_FOOTER_VIEW && viewType != TYPE_NODATE_VIEW && viewType != TYPE_RELOAD_VIEW
    }

    /**
     * StaggeredGridLayoutManager模式时，FooterView可占据一行
     *
     * @param holder
     */
    public override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (isFooterView(holder.getLayoutPosition())) {
            val lp: ViewGroup.LayoutParams? = holder.itemView.getLayoutParams()

            if (lp != null && lp is StaggeredGridLayoutManager.LayoutParams) {
                val p: StaggeredGridLayoutManager.LayoutParams =
                    lp
                p.isFullSpan = true
            }
        }
    }

    /**
     * GridLayoutManager模式时， FooterView可占据一行，判断RecyclerView是否到达底部
     *
     * @param recyclerView
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val layoutManager: RecyclerView.LayoutManager = recyclerView.layoutManager!!
        if (layoutManager is GridLayoutManager) {
            val gridManager: GridLayoutManager = layoutManager
            gridManager.spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (isFooterView(position)) {
                        return gridManager.spanCount
                    }
                    return 1
                }
            }
        }
        startLoadMore(recyclerView, layoutManager)
    }


    /**
     * 判断列表是否滑动到底部
     *
     * @param recyclerView
     * @param layoutManager
     */
    private fun startLoadMore(
        recyclerView: RecyclerView,
        layoutManager: RecyclerView.LayoutManager
    ) {
        if (!mOpenLoadMore || mLoadMoreListener == null) {
            return
        }

        recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!isAutoLoadMore && findLastVisibleItemPosition(layoutManager) + 1 == this@BaseAdapter.itemCount) {
                        scrollLoadMore()
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isAutoLoadMore && findLastVisibleItemPosition(layoutManager) + 1 == this@BaseAdapter.itemCount) {
                    scrollLoadMore()
                } else if (isAutoLoadMore) {
                    isAutoLoadMore = false
                }
            }
        })
    }

    /**
     * 到达底部开始刷新
     */
    private fun scrollLoadMore() {
        if (isReset) {
            return
        }
        if (mFooterLayout.getChildAt(0) === mLoadingView) {
            if (mLoadMoreListener != null) {
                mLoadMoreListener!!.onLoadMore(false)
            }
        }
    }

    private fun findLastVisibleItemPosition(layoutManager: RecyclerView.LayoutManager): Int {
        if (layoutManager is LinearLayoutManager) {
            return (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val lastVisibleItemPositions: IntArray =
                (layoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
            return Util.findMax(lastVisibleItemPositions)
        }
        return -1
    }

    /**
     * 清空footer view
     */
    private fun removeFooterView() {
        mFooterLayout.removeAllViews()
    }

    /**
     * 添加新的footer view
     *
     * @param footerView
     */
    private fun addFooterView(footerView: View?) {
        if (footerView == null) {
            return
        }

        if (mFooterLayout == null) {
            mFooterLayout = RelativeLayout(mContext)
        }
        removeFooterView()
        val params: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        mFooterLayout.addView(footerView, params)
    }

    /**
     * 刷新加载更多的数据
     *
     * @param datas
     */
    fun setLoadMoreData(datas: List<T>) {
        val size = mDatas.size
        mDatas.addAll(datas)
        notifyItemInserted(size)
    }

    /**
     * 下拉刷新，得到的新数据查到原数据起始
     *
     * @param datas
     */
    fun setData(datas: List<T>) {
        mDatas.addAll(0, datas)
        notifyDataSetChanged()
    }

    /**
     * 初次加载、或下拉刷新要替换全部旧数据时刷新数据
     *
     * @param datas
     */
    fun setNewData(datas: List<T>) {
        if (isReset) {
            isReset = false
        }
        mDatas.clear()
        mDatas.addAll(datas)
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        mDatas.removeAt(position)
        notifyDataSetChanged()
    }

    /**
     * 初始化加载中布局
     *
     * @param loadingView
     */
    fun setLoadingView(loadingView: View) {
        mLoadingView = loadingView
        addFooterView(mLoadingView)
    }

    /**
     * 初始加载失败布局
     *
     * @param loadFailedView
     */
    fun setLoadFailedView(loadFailedView: View) {
        mLoadFailedView = loadFailedView
    }

    /**
     * 初始化全部加载完成布局
     *
     * @param loadEndView
     */
    fun setLoadEndView(loadEndView: View) {
        mLoadEndView = loadEndView
    }

    /**
     * 初始化emptyView
     *
     * @param emptyView
     */
    fun setEmptyView(emptyView: View) {
        mEmptyView = emptyView
    }

    /**
     * 移除emptyView
     */
    fun removeEmptyView() {
        isRemoveEmptyView = true
        notifyDataSetChanged()
    }

    /**
     * 初次预加载失败、或无数据可显示该view，进行重新加载或提示用户无数据
     *
     * @param reloadView
     */
    fun setReloadView(reloadView: View) {
        mReloadView = reloadView
        isRemoveEmptyView = true
        notifyDataSetChanged()
    }

    val footerViewCount: Int
        /**
         * 返回 footer view数量
         *
         * @return
         */
        get() = if (mOpenLoadMore && !mDatas.isEmpty()) 1 else 0

    fun setOnLoadMoreListener(loadMoreListener: OnLoadMoreListener) {
        mLoadMoreListener = loadMoreListener
    }

    /**
     * 重置adapter，恢复到初始状态
     */
    fun reset() {
        if (mLoadingView != null) {
            addFooterView(mLoadingView)
        }
        isReset = true
        isAutoLoadMore = true
        mDatas.clear()
    }

    /**
     * 数据加载完成
     */
    fun loadEnd() {
        if (mLoadEndView != null) {
            addFooterView(mLoadEndView)
        }
    }

    /**
     * 数据加载失败
     */
    fun loadFailed() {
        addFooterView(mLoadFailedView)
        mLoadFailedView!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                addFooterView(mLoadingView)
                if (mLoadMoreListener != null) {
                    mLoadMoreListener!!.onLoadMore(true)
                }
            }
        })
    }

    companion object {
        const val TYPE_COMMON_VIEW: Int = 100001 //普通类型 Item
        const val TYPE_FOOTER_VIEW: Int = 100002 //footer类型 Item
        const val TYPE_EMPTY_VIEW: Int = 100003 //empty view，即初始化加载时的提示View
        const val TYPE_NODATE_VIEW: Int = 100004 //初次加载无数据的默认空白view
        const val TYPE_RELOAD_VIEW: Int = 100005 //初次加载无数据的可重新加载或提示用户的view
    }
}
