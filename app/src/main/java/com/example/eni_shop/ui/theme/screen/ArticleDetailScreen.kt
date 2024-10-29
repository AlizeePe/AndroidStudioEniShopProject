package com.example.eni_shop.ui.theme.screen;

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.eni_shop.bo.Article
import com.example.eni_shop.ui.theme.common.EniShopTopBar
import com.example.eni_shop.utils.toFrenchDate
import com.example.eni_shop.vm.ArticleDetailViewModel

@Composable
fun ArticleDetailScreen(
    articleDetailViewModel: ArticleDetailViewModel = viewModel(factory = ArticleDetailViewModel.Factory),
    articleId: Long,
    modifier: Modifier = Modifier
) {
    val article by articleDetailViewModel.article.collectAsState();

    LaunchedEffect(Unit) {
        articleDetailViewModel.getArticleById(articleId)
    }

    Scaffold(topBar = { EniShopTopBar() }) {
        Column(modifier = Modifier.padding(it)) {
            article?.let { it1 -> ArticleDetail(article = it1) }
        }
    }
}


@Composable
fun ArticleDetail(article: Article, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val articleName = " ${article.name}+eni+shop"
    val url = "https://www.google.com/search?q=$articleName"
    Column() {
        Text(
            text = article.name,
            fontSize = 30.sp,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(16.dp)
                .testTag("ArticleName")
                .clickable {
                    Intent(Intent.ACTION_VIEW, Uri.parse(url)).also { intent ->
                        context.startActivity(intent)
                    }
                },
            lineHeight = 1.em,
            textAlign = TextAlign.Justify,
        )
        Surface(
            color = MaterialTheme.colorScheme.inversePrimary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = article.urlImage,
                contentDescription = article.name,
                modifier = Modifier.size(200.dp)
            )
        }
        Text(
            text = article.description,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Justify,
            style = MaterialTheme.typography.bodyMedium
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Prix ${article.price} € ")
            Text(text = "Date de sortie : ${article.date.toFrenchDate()}")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Checkbox(checked = true, onCheckedChange = {})
            Text(text = "Favoris ?")
        }
    }
}

@Composable
@Preview
fun Preview() {
}