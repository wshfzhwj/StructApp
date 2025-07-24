package com.saint.struct.adapter.base.interfaces

import com.saint.struct.adapter.base.BaseViewHolder

interface OnItemClickListener<T> {
    fun onItemClick(baseViewHolder: BaseViewHolder?, data: T?, position: Int)
}
