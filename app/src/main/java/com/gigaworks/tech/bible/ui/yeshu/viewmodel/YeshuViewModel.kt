package com.gigaworks.tech.bible.ui.yeshu.viewmodel

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
class YeshuViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _yeshuList = MutableLiveData<List<SoundEntity>>()
    val yeshuList: LiveData<List<SoundEntity>>
        get() = _yeshuList

    private val _yeshuItemList = MutableLiveData<List<SoundEntity>>()
    val yeshuItemList: LiveData<List<SoundEntity>>
        get() = _yeshuItemList

    fun getYeshuList() {
        viewModelScope.launch {
            _loading.value = true
            _yeshuList.value = repository.getYeshuList()
            _loading.value = false
        }
    }

    fun getYeshuItemList() {
        viewModelScope.launch {
            _loading.value = true
            _yeshuItemList.value = repository.getYeshuItemList()
            _loading.value = false
        }
    }

}