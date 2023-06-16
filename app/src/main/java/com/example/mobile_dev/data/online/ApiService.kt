package com.example.mobile_dev.data.online

import com.example.mobile_dev.data.response.APIResponse
import com.example.mobile_dev.data.response.AgreementResponse
import com.example.mobile_dev.data.response.AuthResponse
import com.example.mobile_dev.data.response.FranchiseResponse
import okhttp3.MultipartBody
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

    @GET("franchises")
    suspend fun getAllFranchise(): FranchiseResponse

    @Multipart
    @POST("franchises")
    suspend fun postFranchise(
        @Header("Authorization") token: String,
        @Part("nama") nama: String,
        @Part("deskripsi") deskripsi: String,
        @Part("requirement") requirement: String,
        @Part logo: MultipartBody.Part,
        @Part dokumen: MultipartBody.Part,
        @Part foto: MultipartBody.Part,
        @Part ("franchise_packages") franchise_packages: ArrayList<String>,
    ): APIResponse

    @GET("franchises/{id}")
    suspend fun getFranchiseByID(
        @Path("id") id: String): FranchiseResponse

    @GET("users/{id}/franchises")
    suspend fun getFranchise(
        @Header("Authorization") token: String,
        @Path("id") id: String): FranchiseResponse

    @Multipart
    @POST("agreements")
    suspend fun postAgreement(
        @Header("Authorization") token: String,
        @Part("franchise_id") franchise_id: String,
        @Part("franchise_package_id") franchise_package_id: String,
        @Part("users_location") users_location: String,
        @Part location_photo: MultipartBody.Part,
        @Part agreement_document: MultipartBody.Part): AgreementResponse


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