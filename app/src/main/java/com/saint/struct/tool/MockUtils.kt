package com.saint.struct.tool

import android.util.Log
import com.saint.struct.model.HomeItem
import kotlinx.coroutines.delay

class MockUtils {
    companion object{
        const val TAG: String = "MockUtils"
    }

    suspend  fun mockData(page: Int): List<HomeItem> {
        delay(1000)
        Log.e(TAG , "page = $page")
        return listOf(
            HomeItem(1, "智能手表1", 599.0, 1500, "https://picsum.photos/200/200?watch", 4.8f),
            HomeItem(2, "无线降噪耳机2", 299.0, 3200, "https://picsum.photos/200/200?earphone", 4.7f),
            HomeItem(3, "便携咖啡机3", 199.0, 890, "https://picsum.photos/200/200?coffee", 4.5f),
            HomeItem(4, "便携咖啡机4", 199.0, 890, "https://picsum.photos/200/200?coffee", 4.5f),
            HomeItem(5, "便携咖啡机5", 199.0, 890, "https://picsum.photos/200/200?coffee", 4.5f),
            HomeItem(6, "便携咖啡机6", 199.0, 890, "https://picsum.photos/200/200?coffee", 4.5f),
            HomeItem(7, "便携咖啡机7", 199.0, 890, "https://picsum.photos/200/200?coffee", 4.5f),
            HomeItem(8, "便携咖啡机8", 199.0, 890, "https://picsum.photos/200/200?coffee", 4.5f),
            HomeItem(9, "便携咖啡机9", 199.0, 890, "https://picsum.photos/200/200?coffee", 4.5f),
            HomeItem(10, "便携咖啡机10", 199.0, 890, "https://picsum.photos/200/200?coffee", 4.5f),
            HomeItem(11, "便携咖啡机11", 199.0, 890, "https://picsum.photos/200/200?coffee", 4.5f),
            HomeItem(12, "便携咖啡机12", 199.0, 890, "https://picsum.photos/200/200?coffee", 4.5f),
            HomeItem(13, "便携咖啡机13", 199.0, 890, "https://picsum.photos/200/200?coffee", 4.5f),
            HomeItem(14, "便携咖啡机14", 199.0, 890, "https://picsum.photos/200/200?coffee", 4.5f),
            HomeItem(15, "便携咖啡机15", 199.0, 890, "https://picsum.photos/200/200?coffee", 4.5f),
            HomeItem(16, "便携咖啡机16", 199.0, 890, "https://picsum.photos/200/200?coffee", 4.5f),
        ).let {
            when (page) {
                1 -> it.take(5)
                2 -> it.filter { it.id >= 6 && it.id <= 10 }
                3 -> it.filter { it.id >= 11 && it.id <= 15 }
                4 -> it.drop(15)
                else -> emptyList()
            }
        }
    }
}