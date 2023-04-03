package com.saint.struct.bean

import java.util.ArrayList

data class WanBean(
    var curPage: Int = 0,
    var size: Int = 0,
    var total: Int = 0,
    var pageCount: Int = 0,
    @JvmField var datas: ArrayList<WanListBean>
)