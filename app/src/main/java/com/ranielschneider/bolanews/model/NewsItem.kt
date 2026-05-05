package com.ranielschneider.bolanews.model

data class NewsItem(
    val id: String,
    val title: String,
    val category: String,
    val source: String,
    val timeAgo: String,
    val imageUrl: String,
    val articleUrl: String,
    val team: String = ""
)