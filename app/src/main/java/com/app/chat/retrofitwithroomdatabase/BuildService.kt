package com.app.chat.retrofitwithroomdatabase

import android.bluetooth.BluetoothClass.Service
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BuildService {
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder().baseUrl("https://dummyjson.com/").addConverterFactory(
        GsonConverterFactory.create()).client(client).build()

    fun <T> serviceBuilder(service: Class<T>):T{
        return retrofit.create(service)
    }
}