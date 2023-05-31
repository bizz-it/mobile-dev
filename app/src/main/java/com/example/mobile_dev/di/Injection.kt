package com.example.mobile_dev.di

import android.content.Context
import com.example.mobile_dev.data.FranchiseRepository
import com.example.mobile_dev.data.offline.FranchiseDatabase
import com.example.mobile_dev.data.online.ApiConfig

object Injection {
    fun provideRepo(context: Context) : FranchiseRepository {
        val apiService = ApiConfig.getApiService()
//        val database = FranchiseDatabase.getInstance(context)
        return FranchiseRepository.getInstance(apiService)
    }
}