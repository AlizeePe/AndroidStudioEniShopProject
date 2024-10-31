package com.example.eni_shop.dao.repository

import com.example.eni_shop.bo.Article
import com.example.eni_shop.dao.ArticleDao
import com.example.eni_shop.dao.network.DaoType

class ArticleRepository(
    private val articleDaoRoomImpl: ArticleDao,
    private val articleDaoMemoryImpl: ArticleDao
) {

    // méthode MEMORY
    fun getArticle(id: Long, type: DaoType = DaoType.MEMORY): Article? {
        return when (type) {
            DaoType.MEMORY -> articleDaoMemoryImpl.findById(id)
            else -> articleDaoRoomImpl.findById(id)
        }
    }

    fun getAllArticles(type: DaoType = DaoType.MEMORY): List<Article> {
        return when (type) {
            DaoType.MEMORY -> articleDaoMemoryImpl.findAll()
            else -> articleDaoRoomImpl.findAll()
        }
    }

    fun addArticle(article: Article, type: DaoType = DaoType.MEMORY): Long {
        return when (type) {
            DaoType.MEMORY -> articleDaoMemoryImpl.insert(article)
            else -> articleDaoRoomImpl.insert(article)
        }
    }

    fun deleteArticle(article: Article, type: DaoType = DaoType.MEMORY) {
        when (type) {
            DaoType.MEMORY -> articleDaoMemoryImpl.deleteArticle(article)
            else -> articleDaoRoomImpl.deleteArticle(article)
        }
    }

}