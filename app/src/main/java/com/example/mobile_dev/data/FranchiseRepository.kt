package com.example.mobile_dev.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.mobile_dev.data.online.ApiService
import com.example.mobile_dev.data.response.APIResponse
import com.example.mobile_dev.data.response.AgreementResponse
import com.example.mobile_dev.data.response.AuthResponse
import com.example.mobile_dev.data.response.FranchiseResponse
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException
class FranchiseRepository private constructor(private val apiService: ApiService){
    private val _auth = MutableLiveData<AuthResponse?>()
    private val auth: LiveData<AuthResponse?> = _auth

    private val _franchise = MutableLiveData<FranchiseResponse>()
    private val franchise: LiveData<FranchiseResponse> = _franchise

    private val _response = MutableLiveData<APIResponse?>()
    private val response: LiveData<APIResponse?> = _response

    private val _agreement = MutableLiveData<AgreementResponse?>()
    private val agreement: LiveData<AgreementResponse?> = _agreement

    fun login(email: String, pass: String): LiveData<Result<AuthResponse?>> = liveData {
        emit(Result.Loading)
        try {
            val auth = apiService.postLogin(email, pass)
            _auth.value = auth
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
        val localData: LiveData<Result<AuthResponse?>> = auth.map { Result.Success(it) }
        emitSource(localData)
    }

    fun register(nama: String, email: String, pass: String, no_telp: String): LiveData<Result<AuthResponse?>> = liveData {
        emit(Result.Loading)
        try {
            val auth = apiService.postRegister(nama, email, pass, no_telp)
            _auth.value = auth
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
        val localData: LiveData<Result<AuthResponse?>> = auth.map { Result.Success(it) }
        emitSource(localData)
    }

    fun getAllFranchise(): LiveData<Result<FranchiseResponse>> = liveData {
        emit(Result.Loading)
        Log.d("PHOTO", "franchise.toTGTRString()")
        try {
            Log.d("PHOTO", "franchise.data.toString()")
            val franchise = apiService.getAllFranchise()
            _franchise.value = franchise
        } catch (t: Throwable) {
            when (t) {
                is HttpException -> {
                    val responseBody = Gson().fromJson(
                        t.response()?.errorBody()?.charStream(),
                        AuthResponse::class.java
                    )
                    emit(Result.Error("${responseBody.message}"))
                    Log.d("PHOTO", "${responseBody.message}")
                }
                is IOException -> {
                    emit(Result.Error(error))
                }
                else -> emit(Result.Error(t.message.toString()))
            }
        }
        val localData: LiveData<Result<FranchiseResponse>> = franchise.map { Result.Success(it) }
        emitSource(localData)
    }

    fun postFranchise(token: String, nama: String, deskripsi: String, requirement: String, logo: File, dokumen: File, foto: File, franchise_category_id: ArrayList<String>): LiveData<Result<APIResponse?>> = liveData {
        emit(Result.Loading)
        val requestLogo = logo.asRequestBody("image/jpeg".toMediaType())
        val requestFoto = foto.asRequestBody("image/jpeg".toMediaType())
        val requestDoc = dokumen.asRequestBody("doc/pdf".toMediaType())
        val fotoPart: MultipartBody.Part = MultipartBody.Part.createFormData("photo", foto.name,requestLogo)
        val logoPart: MultipartBody.Part = MultipartBody.Part.createFormData("logo", logo.name,requestFoto)
        val docPart: MultipartBody.Part = MultipartBody.Part.createFormData("logo", dokumen.name,requestDoc)
        try {
            val response = apiService.postFranchise(token, nama, deskripsi, requirement, logoPart, docPart, fotoPart, franchise_category_id)
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
        val localData: LiveData<Result<APIResponse?>> = response.map { Result.Success(it) }
        emitSource(localData)
    }

    fun getFranchiseByID(id: String): LiveData<Result<FranchiseResponse?>> = liveData {
        emit(Result.Loading)
        try {
            val franchise = apiService.getFranchiseByID(id)
            _franchise.value = franchise
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
        val localData: LiveData<Result<FranchiseResponse?>> = franchise.map { Result.Success(it) }
        emitSource(localData)
    }

    fun postAgreement(token: String,
                      franchise_id: String,
                      franchise_package_id: String,
                      users_location: String,
                      location_photo: File,
                      agreement_document: File): LiveData<Result<AgreementResponse?>> = liveData {
        emit(Result.Loading)
        val requestFoto = location_photo.asRequestBody("image/jpeg".toMediaType())
        val requestDoc = agreement_document.asRequestBody("image/jpeg".toMediaType())
        val fotoPart: MultipartBody.Part = MultipartBody.Part.createFormData("photo", location_photo.name,requestFoto)
        val docPart: MultipartBody.Part = MultipartBody.Part.createFormData("doc", agreement_document.name,requestDoc)
        try {
            val agreement = apiService.postAgreement(token, franchise_id, franchise_package_id, users_location, fotoPart, docPart)
            _agreement.value = agreement
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
        val localData: LiveData<Result<AgreementResponse?>> = agreement.map { Result.Success(it) }
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