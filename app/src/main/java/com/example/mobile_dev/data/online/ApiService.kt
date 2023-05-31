package com.example.mobile_dev.data.online

import com.example.mobile_dev.data.response.AuthResponse
import com.example.mobile_dev.data.response.RegistResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("users/register")
    suspend fun postRegister(
        @Field("nama") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("no_telp") telf: String): AuthResponse

    @FormUrlEncoded
    @POST("users/login")
    suspend fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String): AuthResponse

//    @Multipart
//    @POST("stories")
//    suspend fun postStory(
//        @Header("Authorization") token: String,
//        @Part("description") description: RequestBody,
//        @Part file: MultipartBody.Part,
//        @Part ("lat") lat: Float?,
//        @Part ("lon") lon: Float?,): APIResponse
//
//    @GET("stories")
//    suspend fun getStories(
//        @Header("Authorization") token: String,
//        @Query("page") page: Int,
//        @Query("size") size: Int): StoryResponse
//
//    @GET("stories")
//    suspend fun getMapStories(
//        @Header("Authorization") token: String,
//        @Query("size") size: Int,
//        @Query("location") location: Int): StoryResponse
//
//    @GET("stories/{id}")
//    suspend fun getDetail(
//        @Header("Authorization") token: String,
//        @Path("id") id: String): DetailResponse
}