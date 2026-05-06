package com.ranielschneider.bolanews.repository

import com.ranielschneider.bolanews.api.RetrofitClient
import com.ranielschneider.bolanews.model.NewsItem



class NewsRepository {

    private val apiKey = "49e9836264944b949c37f1ed57dfb8ef"

    suspend fun getFootballNews(team: String = ""): List<NewsItem> {
        val query = if (team.isEmpty())
            "futebol"
        else
            "$team futebol"

        val response = RetrofitClient.service.getNews(
            query    = query,
            language = "pt",
            apiKey   = apiKey
        )

        return response.articles
            .filter { !it.title.isNullOrEmpty() && it.title != "[Removed]" }
            .mapIndexed { index, article ->
                NewsItem(
                    id         = index.toString(),
                    title      = article.title!!,
                    category   = detectCategory(article.title),
                    source     = article.source?.name ?: "Desconhecido",
                    timeAgo    = formatTime(article.publishedAt),
                    imageUrl   = article.urlToImage ?: "",
                    articleUrl = article.url ?: "",
                    team       = team
                )
            }
    }

    private fun detectCategory(title: String?): String {
        if (title == null) return "Futebol"
        return when {
            title.contains("contrat", ignoreCase = true) ||
                    title.contains("transfer", ignoreCase = true) -> "Transferências"
            title.contains("brasileir", ignoreCase = true) -> "Brasileirão"
            title.contains("libertadores", ignoreCase = true) -> "Libertadores"
            title.contains("seleção", ignoreCase = true) -> "Seleção"
            title.contains("copa", ignoreCase = true) -> "Copa"
            else -> "Futebol"
        }
    }

    private fun formatTime(publishedAt: String?): String {
        if (publishedAt == null) return ""
        return try {
            val sdf = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", java.util.Locale.getDefault())
            sdf.timeZone = java.util.TimeZone.getTimeZone("UTC")
            val date = sdf.parse(publishedAt) ?: return ""
            val diff = (System.currentTimeMillis() - date.time) / 1000 / 60
            when {
                diff < 60   -> "${diff}min atrás"
                diff < 1440 -> "${diff / 60}h atrás"
                else        -> "${diff / 1440}d atrás"
            }
        } catch (e: Exception) {
            ""
        }
    }
}