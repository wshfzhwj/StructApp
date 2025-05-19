package com.saint.struct.repository

import com.saint.struct.network.ServiceCreator
import com.saint.struct.network.service.WanAndroidKtApi

object Repository {
    suspend fun getArticleList(page: Int, id: Int) =
        ServiceCreator.create<WanAndroidKtApi>().getArticleList(page, id)

    suspend fun getProjectsList(page: Int, id: Int) =
        ServiceCreator.create<WanAndroidKtApi>().getProjects(page, id)
}