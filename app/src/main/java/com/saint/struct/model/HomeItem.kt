package com.saint.struct.model

data class HomeItem(
    val id: Long,
    val name: String,
    val price: Double,
    val sales: Int,
    val imageUrl: String,
    val rating: Float
) {
    override fun equals(other: Any?): Boolean {
        return other is HomeItem &&
                id == other.id &&
                imageUrl == other.imageUrl  // 确保比较图片URL
    }
}