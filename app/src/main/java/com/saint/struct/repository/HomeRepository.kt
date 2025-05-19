package com.saint.struct.repository

import com.saint.struct.model.HomeItem

interface HomeRepository {
    suspend fun getHomeData(page: Int): List<HomeItem>
}
