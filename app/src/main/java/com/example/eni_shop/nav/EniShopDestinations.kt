package com.example.eni_shop.nav

import androidx.navigation.NavType
import androidx.navigation.navArgument


interface Destination {
    val route: String
}

object ArticleListDestination : Destination {
    override val route: String
        get() = "articleList"
}

object ArticleDetailDestination : Destination {
    override val route: String
        get() = "articleDetail"
    val argName = "articleId"
    var args = listOf(
        navArgument(argName) {
            type = NavType.LongType
        }
    )
    val routeArticleDetail = "$route/{$argName}"
}

object ArticleFormDestination : Destination {
    override val route: String
        get() = "articleForm"
}