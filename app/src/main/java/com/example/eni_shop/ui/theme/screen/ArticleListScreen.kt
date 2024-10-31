package com.example.eni_shop.ui.theme.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.eni_shop.bo.Article
import com.example.eni_shop.ui.theme.common.EniShopScaffold
import com.example.eni_shop.ui.theme.common.LoadingScreen
import com.example.eni_shop.vm.ArticleListViewModel

@Composable
fun ArticleListScreen(
    articleListViewModel: ArticleListViewModel = viewModel(factory = ArticleListViewModel.Factory),
    onClickToArticleDetail: (Long) -> Unit,
    onClickToAddArticle: () -> Unit
) {
    val articles by articleListViewModel.articles.collectAsState()
    val categories by articleListViewModel.categories.collectAsState()
    val isLoading by articleListViewModel.isLoading.collectAsState()

    if (isLoading) {
        LoadingScreen()
    } else {

        var category by remember {
            mutableStateOf("")
        }

        val filteredArticles = if (category != "") {
            articles.filter {
                it.category == category
            }
        } else {
            articles
        }

        EniShopScaffold(
            floatingActionButton = { ArticleListFAB(onClickToAddArticle = onClickToAddArticle) },
            bottomBar = {
                ArticleListBottomBar(
                    onClickToHome = { articleListViewModel.getAllArticles() },
                    onClickToFav = { articleListViewModel.getArticlesFav() })
            }) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            ) {
                CategoryFilterChip(
                    categories = categories,
                    category = category,
                    onCategoryChange = { category = it })
                ArticleList(
                    articles = filteredArticles,
                    onClickToArticleDetail = onClickToArticleDetail
                )
            }
        }
    }
}

@Composable
fun ArticleItem(
    article: Article,
    modifier: Modifier = Modifier,
    onClickToArticleDetail: (Long) -> Unit
) {

    Card(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        modifier = Modifier.clickable { onClickToArticleDetail(article.id) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            AsyncImage(
                model = article.urlImage,
                contentDescription = article.name,
                modifier = Modifier
                    .size(80.dp)
                    .border(1.dp, MaterialTheme.colorScheme.inverseSurface, CircleShape)
                    .padding(8.dp)
            )
            Text(
                text = article.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                minLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(8.dp)
            )
            Text(text = " ${String.format("%.2f", article.price)} € ")
        }
    }
}

@Composable
fun ArticleList(
    articles: List<Article>,
    modifier: Modifier = Modifier,
    onClickToArticleDetail: (Long) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(articles) { article ->
            ArticleItem(article = article, onClickToArticleDetail = onClickToArticleDetail)
        }
    }
}


@Composable
fun CategoryFilterChip(
    categories: List<String>,
    category: String,
    onCategoryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(categories) {
            FilterChip(
                onClick = {
                    if (category != it) {
                        onCategoryChange(it)
                    } else {
                        onCategoryChange("")
                    }
                },
                label = {
                    Text(text = it)
                },
                selected = category == it,
                leadingIcon = if (category == it) {
                    {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else {
                    null
                },
            )
        }
    }

}

@Composable
fun ArticleListFAB(onClickToAddArticle: () -> Unit) {
    FloatingActionButton(
        onClick = onClickToAddArticle,
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 4.dp)
    ) {
        Image(
            imageVector = Icons.Default.Add,
            contentDescription = "Add article",
            modifier = Modifier.size(40.dp)
        )
    }
}

@Composable
fun ArticleListBottomBar(onClickToHome: () -> Unit, onClickToFav: () -> Unit) {
    BottomAppBar() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = onClickToHome) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    modifier = Modifier.size(40.dp)
                )
            }
            IconButton(onClick = onClickToFav) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Favoris",
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

@Composable
@Preview
fun ListPreview() {
    //ArticleListBottomBar()
}