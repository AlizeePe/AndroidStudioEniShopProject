package com.example.eni_shop.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.eni_shop.dao.repository.ArticleRepository
import com.example.eni_shop.db.EniShopDatabase
import com.example.eni_shop.services.ShopService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ArticleFormViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: MutableStateFlow<List<String>> = _categories

    init {
        getAllCategories()
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
                return ArticleFormViewModel(
                    ArticleRepository(
                        EniShopDatabase.getInstance(application.applicationContext).getArticleDao(),
                        ShopService.ShopApi.retrofitService
                    )
                ) as T
            }
        }
    }

}