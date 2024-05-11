package com.app.chat.retrofitwithroomdatabase

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("products")
    fun getdataproduct():Call<Prodectlist>
}