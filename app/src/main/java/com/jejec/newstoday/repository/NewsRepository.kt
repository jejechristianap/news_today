package com.jejec.newstoday.repository

import com.jejec.newstoday.api.RetrofitInstance
import com.jejec.newstoday.api.RetrofitInstance.Companion.api
import com.jejec.newstoday.db.ArticleDatabase
import com.jejec.newstoday.model.Article

class NewsRepository(private val db: ArticleDatabase) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        api.searchForNews(searchQuery, pageNumber)

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)
}