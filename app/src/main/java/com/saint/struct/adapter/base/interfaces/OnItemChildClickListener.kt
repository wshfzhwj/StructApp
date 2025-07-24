package com.saint.struct.adapter.base.interfaces

import com.saint.struct.adapter.base.BaseViewHolder

interface OnItemChildClickListener<T> {
    fun onItemChildClick(baseViewHolder: BaseViewHolder?, data: T?, position: Int)
}
