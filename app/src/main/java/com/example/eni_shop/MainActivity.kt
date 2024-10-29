package com.example.eni_shop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eni_shop.nav.ArticleDetailDestination
import com.example.eni_shop.nav.ArticleFormDestination
import com.example.eni_shop.nav.ArticleListDestination
import com.example.eni_shop.ui.theme.common.NavigationBackIcon
import com.example.eni_shop.ui.theme.screen.ArticleDetailScreen
import com.example.eni_shop.ui.theme.screen.ArticleFormScreen
import com.example.eni_shop.ui.theme.screen.ArticleListScreen

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EniShopApp()
        }
    }

    @Composable
    fun EniShopApp() {
        val navController = rememberNavController()
        EniShopNavHost(navController = navController)
    }

    @Composable
    fun EniShopNavHost(navController: NavHostController) {
        NavHost(
            navController = navController,
            startDestination = ArticleListDestination.route
        ) {
            composable(route = ArticleListDestination.route) {
                ArticleListScreen(
                    onClickToAddArticle = {
                        navController.navigate(ArticleFormDestination.route)
                    },
                    onClickToArticleDetail = { articleId -> navController.navigate("${ArticleDetailDestination.route}/$articleId") }
                )

            }
            composable(route = ArticleFormDestination.route) {
                ArticleFormScreen(navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                        NavigationBackIcon(onClickToBack = {
                            navController.popBackStack()
                        })
                    }
                })
            }
            composable(
                route = ArticleDetailDestination.routeArticleDetail,
                arguments = ArticleDetailDestination.args
            ) {
                val articleId = it.arguments?.getLong(ArticleDetailDestination.argName)
                ArticleDetailScreen(articleId = articleId!!, navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                        NavigationBackIcon(onClickToBack = {
                            navController.popBackStack()
                        })
                    }
                }
                )
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        // ArticleListScreen();
    }
}