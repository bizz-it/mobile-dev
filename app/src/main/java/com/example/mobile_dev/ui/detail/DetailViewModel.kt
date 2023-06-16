package com.example.mobile_dev.ui.detail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobile_dev.data.FranchiseRepository
import com.example.mobile_dev.di.Injection

class DetailViewModel(private val franchiseRepository: FranchiseRepository) : ViewModel() {
    fun detailFranchise(id: String) = franchiseRepository.getFranchiseByID(id)
}

class ViewModelFactory (private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(Injection.provideRepo(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}