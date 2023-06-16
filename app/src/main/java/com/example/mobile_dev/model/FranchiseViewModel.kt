package com.example.mobile_dev.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobile_dev.data.FranchiseRepository
import com.example.mobile_dev.di.Injection

class FranchiseViewModel(private val franchiseRepository: FranchiseRepository) : ViewModel() {
    fun agreement() = franchiseRepository.getAllFranchise()
}

class ViewModelFactory (private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FranchiseViewModel::class.java)) {
            return FranchiseViewModel(Injection.provideRepo(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}