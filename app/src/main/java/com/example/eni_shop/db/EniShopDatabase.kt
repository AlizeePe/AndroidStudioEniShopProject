package com.example.eni_shop.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.eni_shop.bo.Article
import com.example.eni_shop.dao.ArticleDao
import com.example.eni_shop.utils.Converters


@Database(entities = [Article::class], version = 1)
@TypeConverters(Converters::class)
abstract class EniShopDatabase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    companion object {
        private var instance: EniShopDatabase? = null
        fun getInstance(context: Context): EniShopDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context = context,
                    klass = EniShopDatabase::class.java,
                    name = "eni_shop.db"
                ).fallbackToDestructiveMigration().build()
            }
            return instance as EniShopDatabase
        }
    }
}
