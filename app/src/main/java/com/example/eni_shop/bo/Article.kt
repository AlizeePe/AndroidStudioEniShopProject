package com.example.eni_shop.bo

import java.util.Date

data class Article(var id: Long, var name: String, var description: String, var price : Double, var urlImage: String, var category: String, var date: Date)
