package com.seuapp.futebolnews.model

data class NewsItem(
    val id: String,
    val title: String,
    val category: String,   // ex: "Brasileirão", "Transferências"
    val source: String,     // ex: "GloboEsporte"
    val timeAgo: String,    // ex: "2h atrás"
    val imageUrl: String,
    val articleUrl: String,
    val team: String = ""   // time relacionado, vazio = notícia geral
)