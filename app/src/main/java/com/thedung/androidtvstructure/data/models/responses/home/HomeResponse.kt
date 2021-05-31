package com.thedung.androidtvstructure.data.models.responses.home

import com.google.gson.annotations.SerializedName
import com.thedung.androidtvstructure.data.models.entities.home.HomeGroupEntity
import com.thedung.androidtvstructure.data.models.entities.home.HomeGroupItemEntity

data class HomeGroupResponse(
    @SerializedName("group_name")
    val groupName: String?,
    @SerializedName("items")
    val items: List<HomeGroupItemResponse>?
) {
    fun toEntity(): HomeGroupEntity {
        val list = arrayListOf<HomeGroupItemEntity>()
        items?.run {
            for (item in this) {
                list.add(item.toEntity())
            }
        }

        return HomeGroupEntity(
            this.groupName.orEmpty(),
            list
        )
    }
}

data class HomeGroupItemResponse(
    @SerializedName("name")
    val title: String?,
    @SerializedName("image")
    val img: String?,
    @SerializedName("detail_url")
    val detailUrl: String?
) {
    fun toEntity(): HomeGroupItemEntity {
        return HomeGroupItemEntity(
            this.title.orEmpty(),
            this.img.orEmpty(),
            this.detailUrl.orEmpty()
        )
    }
}

fun List<HomeGroupResponse>.toEntity(): List<HomeGroupEntity> {
    val list = arrayListOf<HomeGroupEntity>()
    for (item in this) {
        list.add(item.toEntity())
    }
    return list
}