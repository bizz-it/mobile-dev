package com.example.mobile_dev.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.mobile_dev.data.online.ApiService
import com.example.mobile_dev.data.response.AuthResponse
import com.example.mobile_dev.data.Result
import retrofit2.HttpException

class FranchiseRepository private constructor(private val apiService: ApiService){
    private val _response = MutableLiveData<AuthResponse?>()
    private val response: LiveData<AuthResponse?> = _response

    fun login(email: String, pass: String): LiveData<Result<AuthResponse?>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.postLogin(email, pass)
            _response.value = response

        } catch (e: Exception) {
            Log.d("StoryRepository", "${e.message.toString()} ")
            emit(Result.Error( "${e.message.toString()} "))
        }
        val localData: LiveData<Result<AuthResponse?>> = response.map { Result.Success(it) }
        emitSource(localData)
    }

    fun register(nama: String, email: String, pass: String, no_telp: String): LiveData<Result<AuthResponse?>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.postRegister(nama, email, pass, no_telp)
            _response.value = response
        } catch (e: Exception) {
            Log.d("StoryRepository", "${e.localizedMessage} ")
            emit(Result.Error( "${e.localizedMessage} "))
        }
        val localData: LiveData<Result<AuthResponse?>> = response.map { Result.Success(it) }
        emitSource(localData)
    }


    companion object {
        @Volatile
        private var instance: FranchiseRepository? = null
        fun getInstance(
            apiService: ApiService
        ): FranchiseRepository =
            instance ?: synchronized(this) {
                instance ?: FranchiseRepository(apiService)
            }.also { instance = it }
    }
}