package com.saint.struct.bean

data class ProjectData(
    val curPage: Int,
    val datas: List<Project>,
    val pageCount: Int
)