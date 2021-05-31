package com.thedung.androidtvstructure.data.models.entities.home

data class HomeGroupEntity(
    val groupName: String,
    val items: List<HomeGroupItemEntity>
)

data class HomeGroupItemEntity(
    val title: String,
    val img: String,
    val detailUrl: String
)