package com.example.eni_shop.dao.network

import com.example.eni_shop.dao.ArticleDao
import com.example.eni_shop.dao.memory.ArticleDaoMemoryImpl

abstract class DaoFactory {
    companion object {
        fun createArticleDao(type: DaoType):
                ArticleDao {
            val dao: ArticleDao
            when (type) {
                DaoType.MEMORY -> dao = ArticleDaoMemoryImpl()
                DaoType.NETWORK -> TODO()
            }
            return dao;
        }
    }
}