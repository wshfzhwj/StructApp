package com.saint.struct.bean

import java.util.ArrayList

class WanBean {
    var curPage = 0
    var size = 0
    var total = 0
    var pageCount = 0
    @JvmField
    var datas = ArrayList<WanListBean>()
}