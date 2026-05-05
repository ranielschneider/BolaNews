package com.ranielschneider.bolanews.model

import com.google.gson.annotations.SerializedName

// Representa o JSON completo que a NewsAPI retorna
data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

// Representa cada artigo dentro do JSON
data class Article(
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val source: ArticleSource?
)

data class ArticleSource(
    val id: String?,
    val name: String?
)