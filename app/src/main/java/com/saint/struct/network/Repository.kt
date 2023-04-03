package com.saint.struct.network

import com.saint.struct.network.service.WanAndroidKtApi

object Repository {
    suspend fun getProjectsList(page: Int, id: Int) =
        ServiceCreator.create<WanAndroidKtApi>().getProjects(page, id)
}