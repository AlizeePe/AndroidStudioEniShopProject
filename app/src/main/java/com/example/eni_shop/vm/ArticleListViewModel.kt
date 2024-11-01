package com.example.eni_shop.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.eni_shop.bo.Article
import com.example.eni_shop.dao.network.DaoType
import com.example.eni_shop.dao.repository.ArticleRepository
import com.example.eni_shop.db.EniShopDatabase
import com.example.eni_shop.services.ShopService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArticleListViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: MutableStateFlow<List<String>> = _categories

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val cat = async { _categories.value = articleRepository.getAllCategories() }
            val art = async { _articles.value = articleRepository.getAllArticles() }
            awaitAll(cat, art)
            _isLoading.value = false
        }
    }

    fun getArticlesFav() {
        viewModelScope.launch(Dispatchers.IO) {
            _articles.value = articleRepository.getAllArticles(type = DaoType.ROOM)
        }
    }

    fun getAllArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            _articles.value = articleRepository.getAllArticles()
        }
    }

    fun getAllCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _categories.value = articleRepository.getAllCategories()
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
                        ShopService.ShopApi.retrofitService
                    )
                ) as T
            }
        }
    }
}