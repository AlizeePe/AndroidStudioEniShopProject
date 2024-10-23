package com.example.eni_shop.dao.repository

import com.example.eni_shop.bo.Article
import com.example.eni_shop.dao.ArticleDao

import com.example.eni_shop.dao.network.DaoFactory
import com.example.eni_shop.dao.network.DaoType

object ArticleRepository {
    private val articleDao: ArticleDao = DaoFactory.createArticleDao(DaoType.MEMORY);

    fun getArticle(id: Long): Article? {
        return articleDao.findById(id);
    }
    fun addArticle(article: Article): Long {
        return articleDao.insert(article);
    }

}