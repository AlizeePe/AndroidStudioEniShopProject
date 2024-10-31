package com.example.eni_shop.dao.repository

import com.example.eni_shop.services.ShopService
import com.example.eni_shop.bo.Article
import com.example.eni_shop.dao.ArticleDao
import com.example.eni_shop.dao.network.DaoType

class ArticleRepository(
    private val articleDaoRoomImpl: ArticleDao,
    private val shopService: ShopService
) {

    suspend fun getArticle(id: Long, type: DaoType = DaoType.NETWORK): Article? {
        return when (type) {
            DaoType.NETWORK -> shopService.getArticleById(id)
            else -> articleDaoRoomImpl.findById(id)
        }
    }

    suspend fun getAllArticles(type: DaoType = DaoType.NETWORK): List<Article> {
        return when (type) {
            DaoType.NETWORK -> shopService.getAllArticles()
            else -> articleDaoRoomImpl.findAll()
        }
    }

    suspend fun addArticle(article: Article, type: DaoType = DaoType.NETWORK): Any {
        return when (type) {
            DaoType.NETWORK -> shopService.addArticle(article)
            else -> articleDaoRoomImpl.insert(article)
        }
    }

    fun deleteArticle(article: Article, type: DaoType = DaoType.NETWORK) {
        articleDaoRoomImpl.deleteArticle(article)
    }

    suspend fun getAllCategories(): List<String> {
        return shopService.getAllCategories()
    }

}