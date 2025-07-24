package com.saint.struct.adapter.base.interfaces

import com.saint.struct.adapter.base.BaseViewHolder

interface OnSwipeMenuClickListener<T> {
    fun onSwipeMenuClick(baseViewHolder: BaseViewHolder?, data: T?, position: Int)
}
