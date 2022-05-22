package com.zerdasoftware.thebooks.service


import com.zerdasoftware.thebooks.model.ObjectData
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class BookAPIService {

    //Hangi API'nin base linkini belirtiyoruz ve bağlantıyı başlatıyoruz
    private val BASE_URL = "https://api.nytimes.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(BookAPI::class.java)

    //çekilecek datanın İlk modelimiz olan ObjectData modelinde Single yaparak rxJava ile tek seferlik çekim yapılacağını belirtiyoruz
    fun getData(): Single<ObjectData> {
        return api.getObjectData()
    }
}