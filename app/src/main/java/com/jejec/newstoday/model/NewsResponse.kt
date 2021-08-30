package com.jejec.newstoday.model

data class NewsResponse(
    val articles: MutableList<Article>?,
    val status: String?,
    val totalResults: Int
)