package com.example.foodrecipes.Api

import com.example.foodrecipes.Model.LoginData
import com.example.foodrecipes.Model.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Apiinterface {
    @POST("login")
    fun requestdata(@Body login:LoginData):Call<Response>

    @POST("signup")
    fun signuprequestdata(@Body signup:LoginData):Call<Response>
}