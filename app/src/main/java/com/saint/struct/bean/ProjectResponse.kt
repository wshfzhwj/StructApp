package com.saint.struct.bean

data class ProjectResponse(
    val data: Data
)

data class Data(
    val curPage: Int,
    val datas: List<Project>,
    val pageCount: Int
)

data class Project(
    val id: String,
    val author: String,
    val envelopePic: String,
    val title: String,
    val niceDate: String,
    val desc: String
)