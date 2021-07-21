package com.gigaworks.tech.bible.ui.bible.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gigaworks.tech.bible.cache.entity.BookEntity
import com.gigaworks.tech.bible.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BibleViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _books = MutableLiveData<List<BookEntity>>()
    val books: LiveData<List<BookEntity>>
    get() = _books

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    fun getBooksByCategory(category: String) {
        viewModelScope.launch {
            _loading.value = true
            _books.value = repository.getBooksByCategory(category)
            _loading.value = false
        }
    }

}