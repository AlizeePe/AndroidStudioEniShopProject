package com.example.eni_shop.dao

import com.example.eni_shop.bo.Article

interface ArticleDao {
    fun findById(id: Long): Article?;
    fun insert(article: Article): Long;
    fun findAll(): List<Article>
}