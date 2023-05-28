package com.example.mobile_dev.ui.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobile_dev.data.FranchiseRepository
import com.example.mobile_dev.di.Injection

class AuthViewModel (private val franchiseRepository: FranchiseRepository) : ViewModel() {
    fun login(email: String, pass: String) = franchiseRepository.login(email, pass)
    fun register(nama: String, email: String, pass: String, no_telp: String) =
        franchiseRepository.register(nama, email, pass, no_telp)
}

class ViewModelFactory (private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(Injection.provideRepo(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}