package com.zerdasoftware.thebooks.service

import com.zerdasoftware.thebooks.model.Books
import com.zerdasoftware.thebooks.model.ObjectData
import io.reactivex.Single
import retrofit2.http.GET

interface BookAPI {


    @GET("svc/books/v3/lists/current/hardcover-fiction.json?api-key=00PsW6aHH5rv0VM44d9g6eFTeRGAzBAB")
    fun getObjectData () : Single <ObjectData>
    //Single<List<Book>>
}