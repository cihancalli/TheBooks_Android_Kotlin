package com.zerdasoftware.thebooks.service

import com.zerdasoftware.thebooks.model.Books
import com.zerdasoftware.thebooks.model.ObjectData
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class BookAPIService {

    private val BASE_URL = "https://api.nytimes.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(BookAPI::class.java)

    fun getData(): Single<ObjectData> {
        return api.getObjectData()
    }
}