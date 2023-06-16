package com.example.mobile_dev.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobile_dev.data.response.DataItem
import kotlinx.coroutines.flow.MutableStateFlow

class DataViewModel (list: List<DataItem>) : ViewModel() {
    private val _movieList = MutableStateFlow(
        list.sortedBy { it.id }
    )
    val movieList: MutableStateFlow<List<DataItem>> get() = _movieList
}

class ViewModelFactoryDua(private val list: List<DataItem>) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DataViewModel::class.java)) {
            return DataViewModel(list) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
