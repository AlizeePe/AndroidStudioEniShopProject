package com.example.eni_shop.services

import com.example.eni_shop.bo.Article
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ShopService {
    companion object {
        val BASE_URL = "https://fakestoreapi.com/"
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    // méthode pour récupérer l'ensemble des articles
    @GET("products")
    suspend fun getAllArticles(): List<Article>

    // méthode pour récupérer un article en fonction de son id
    @GET("products/{id}")
    suspend fun getArticleById(@Path("id") id: Long) : Article

    // méthode pour ajouter un article
    @POST("products")
    suspend fun addArticle(@Body article: Article) : Article

    // méthode pour récupérer toutes les catégories
    @GET("products/categories")
    suspend fun getAllCategories(): List<String>

    object ShopApi {
        val retrofitService: ShopService by lazy { retrofit.create(ShopService::class.java) }
    }
}