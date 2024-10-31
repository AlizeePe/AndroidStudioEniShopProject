package com.example.eni_shop.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.eni_shop.bo.Article

@Dao
interface ArticleDao {

    @Query("SELECT * FROM Article Where id = :id")
    fun findById(id: Long): Article?;

    @Insert
    fun insert(article: Article): Long;

    @Query("SELECT * FROM Article")
    fun findAll(): List<Article>

    @Delete
    fun deleteArticle(article: Article)
}