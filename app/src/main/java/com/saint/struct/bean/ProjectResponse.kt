package com.saint.struct.bean

data class ProjectResponse(
    val data: ProjectData,
    var errorCode: Int = 0,
    var errorMsg: String? = null
)