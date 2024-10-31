package com.example.eni_shop.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.eni_shop.bo.Article
import com.example.eni_shop.dao.network.DaoFactory
import com.example.eni_shop.dao.network.DaoType
import com.example.eni_shop.dao.repository.ArticleRepository
import com.example.eni_shop.db.EniShopDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArticleListViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: MutableStateFlow<List<String>> = _categories

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles

    init {
        getArticles()
        _categories.value =
            listOf("electronics", "jewelry", "men's clothing", "women's clothing")

    }

    fun getArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            _articles.value = articleRepository.getAllArticles()
        }
    }

    fun getArticlesFav() {
        viewModelScope.launch(Dispatchers.IO) {
            _articles.value = articleRepository.getAllArticles(type = DaoType.ROOM)
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])
                return ArticleListViewModel(
                    ArticleRepository(
                        EniShopDatabase.getInstance(application.applicationContext).getArticleDao(),
                        DaoFactory.createArticleDao(DaoType.MEMORY)
                    )
                ) as T
            }
        }
    }

}