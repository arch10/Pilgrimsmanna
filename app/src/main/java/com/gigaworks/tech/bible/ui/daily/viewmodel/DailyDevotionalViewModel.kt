package com.gigaworks.tech.bible.ui.daily.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gigaworks.tech.bible.cache.entity.SoundEntity
import com.gigaworks.tech.bible.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyDevotionalViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _books = MutableLiveData<List<SoundEntity>>()
    val books: LiveData<List<SoundEntity>>
        get() = _books

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _dailyDevotionals = MutableLiveData<List<SoundEntity>>()
    val dailyDevotionals: LiveData<List<SoundEntity>>
        get() = _dailyDevotionals

    init {
        getDailyDevotionalTitles()
    }

    private fun getDailyDevotionalTitles() {
        viewModelScope.launch {
            _loading.value = true
            _books.value = repository.getDailyDevotional()
            _loading.value = false
        }
    }

    fun getDailyDevotionals(mainCategory: String, category: String) {
        viewModelScope.launch {
            _loading.value = true
            _dailyDevotionals.value =
                repository.getDailyDevotionalByCategory(mainCategory, category)
            _loading.value = false
        }
    }

}