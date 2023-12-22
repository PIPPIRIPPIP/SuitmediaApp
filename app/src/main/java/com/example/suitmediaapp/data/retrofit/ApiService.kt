package com.example.suitmediaapp.data.retrofit

import com.example.suitmediaapp.data.response.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getUsers(
        @Query("page") currentPage: Int,
        @Query("per_page") perPage: Int = 10
    ): Call<ApiResponse>
}