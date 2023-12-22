package com.example.suitmediaapp.view

import androidx.lifecycle.ViewModel
import com.example.suitmediaapp.data.retrofit.ApiConfig

class ScreenViewModel : ViewModel() {
    private val apiService = ApiConfig.getApiService()

    fun getUser(pageNumber: Int) = apiService.getUsers(pageNumber)
}