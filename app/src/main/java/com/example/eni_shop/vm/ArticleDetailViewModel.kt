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

class ArticleDetailViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    private val _currentArticle = MutableStateFlow<Article?>(null)
    val currentArticle: MutableStateFlow<Article?> = _currentArticle

    private val _isArticleFav = MutableStateFlow<Boolean>(false)
    val isArticleFav: StateFlow<Boolean> = _isArticleFav

    fun getArticleById(id: Long) {
        _currentArticle.value = articleRepository.getArticle(id)
    }

    // récupération de l'article depuis la BDD
    // afin de savoir si il est dans nos favoris
    fun getArticleFav(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val article = articleRepository.getArticle(id, type = DaoType.ROOM)
            if (article != null) {
                _isArticleFav.value = true
            }
        }
    }

    fun saveArticleFav() {
        viewModelScope.launch(Dispatchers.IO) {
            _currentArticle.value?.let { articleRepository.addArticle(it, type = DaoType.ROOM) }
            _isArticleFav.value = true
        }
    }

    fun deleteArticleFav() {
        viewModelScope.launch(Dispatchers.IO) {
            _currentArticle.value?.let { articleRepository.deleteArticle(it, type = DaoType.ROOM) }
            _isArticleFav.value = false
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
                return ArticleDetailViewModel(
                    ArticleRepository(
                        EniShopDatabase.getInstance(application.applicationContext).getArticleDao(),
                        DaoFactory.createArticleDao(DaoType.MEMORY)
                    )
                ) as T
            }
        }
    }
}