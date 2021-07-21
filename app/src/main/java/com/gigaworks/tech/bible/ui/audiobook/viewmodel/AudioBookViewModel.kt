package com.gigaworks.tech.bible.ui.audiobook.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gigaworks.tech.bible.cache.entity.BookEntity
import com.gigaworks.tech.bible.cache.entity.SoundEntity
import com.gigaworks.tech.bible.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioBookViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _books = MutableLiveData<List<SoundEntity>>()
    val books: LiveData<List<SoundEntity>>
        get() = _books

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    init {
        getAudioBooks()
    }

    private fun getAudioBooks() {
        viewModelScope.launch {
            _loading.value = true
            _books.value = repository.getAudioBooks()
            _loading.value = false
        }
    }

}