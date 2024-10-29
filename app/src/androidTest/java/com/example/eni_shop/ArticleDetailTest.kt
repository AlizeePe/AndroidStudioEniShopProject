package com.example.eni_shop

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.eni_shop.ui.theme.screen.ArticleDetailScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticleDetailTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun ArticleDetailTest() {
        composeRule.setContent {
            ArticleDetailScreen(articleId = 2)
        }
        composeRule
            .onNodeWithTag("ArticleName")
            .assertExists("Titre non trouvé !")
            .assertTextEquals("Mens Casual Premium Slim Fit T-Shirts")
    }
}