package com.zerdasoftware.thebooks.service


import com.zerdasoftware.thebooks.model.ObjectData
import io.reactivex.Single
import retrofit2.http.GET

interface BookAPI {

    //apimizden nerden ve nasıl data çekileceğini belirtiyoruz
    @GET("svc/books/v3/lists/current/hardcover-fiction.json?api-key=00PsW6aHH5rv0VM44d9g6eFTeRGAzBAB")
    //get fonksiyonumuz ile Single ile rxJavadan tek seferlik ObjectData modelinde data çekiyoruz
    fun getObjectData () : Single <ObjectData>
}