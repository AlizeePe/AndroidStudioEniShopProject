package com.example.eni_shop.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.eni_shop.bo.Article
import com.example.eni_shop.dao.repository.ArticleRepository
import kotlinx.coroutines.flow.MutableStateFlow

class ArticleDetailViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    private val _article = MutableStateFlow<Article?>(null)
    val article: MutableStateFlow<Article?> = _article

    fun getArticleById(id: Long) {
        _article.value = articleRepository.getArticle(id)
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                return ArticleDetailViewModel(
                    ArticleRepository()
                ) as T
            }
        }
    }
}