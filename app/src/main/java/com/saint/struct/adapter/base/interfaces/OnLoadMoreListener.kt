package com.saint.struct.adapter.base.interfaces


interface OnLoadMoreListener {
    /**
     * 加载更多的回调方法
     * @param isReload 是否是重新加载，只有加载失败后，点击重新加载时为true
     */
    fun onLoadMore(isReload: Boolean)
}
