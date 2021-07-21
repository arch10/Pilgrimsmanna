package com.gigaworks.tech.bible.ui.home.viewmodel

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
class MainViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _error = MutableLiveData("")
    val error: LiveData<String>
        get() = _error

    fun getData() {
        viewModelScope.launch {
            val bookError = repository.getAllBooksFromNetwork()
            val soundError = repository.getAllSoundFromNetwork()
            if (bookError || soundError) {
                _error.value = "Error getting data"
            }
        }
    }

}