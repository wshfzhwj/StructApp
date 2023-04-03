package com.saint.struct.bean

data class WanAndroidBean(
    @JvmField var data: WanBean? = null,
    var errorCode: Int = 0,
    var errorMsg: String? = null
)