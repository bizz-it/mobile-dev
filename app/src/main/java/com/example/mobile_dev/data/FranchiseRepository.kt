package com.example.mobile_dev.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.mobile_dev.data.online.ApiService
import com.example.mobile_dev.data.response.AuthResponse
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException
class FranchiseRepository private constructor(private val apiService: ApiService){
    private val _response = MutableLiveData<AuthResponse?>()
    private val response: LiveData<AuthResponse?> = _response

    fun login(email: String, pass: String): LiveData<Result<AuthResponse?>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.postLogin(email, pass)
            _response.value = response
        } catch (t: Throwable) {
            when (t) {
                is HttpException -> {
                    val responseBody = Gson().fromJson(
                        t.response()?.errorBody()?.charStream(),
                        AuthResponse::class.java
                    )
                    emit(Result.Error("${responseBody.message}"))
                }
                is IOException -> {
                    emit(Result.Error(error))
                }
                else -> emit(Result.Error(t.message.toString()))
            }
        }
        val localData: LiveData<Result<AuthResponse?>> = response.map { Result.Success(it) }
        emitSource(localData)
    }

    fun register(nama: String, email: String, pass: String, no_telp: String): LiveData<Result<AuthResponse?>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.postRegister(nama, email, pass, no_telp)
            _response.value = response
        } catch (t: Throwable) {
            when (t) {
                is HttpException -> {
                    val responseBody = Gson().fromJson(
                        t.response()?.errorBody()?.charStream(),
                        AuthResponse::class.java
                    )
                    emit(Result.Error("${responseBody.message}"))
                }
                is IOException -> {
                    emit(Result.Error(error))
                }
                else -> emit(Result.Error(t.message.toString()))
            }
        }
        val localData: LiveData<Result<AuthResponse?>> = response.map { Result.Success(it) }
        emitSource(localData)
    }


    companion object {
        @Volatile
        private var instance: FranchiseRepository? = null
        private var error: String = "Can't connect to server"
        fun getInstance(
            apiService: ApiService
        ): FranchiseRepository =
            instance ?: synchronized(this) {
                instance ?: FranchiseRepository(apiService)
            }.also { instance = it }
    }
}