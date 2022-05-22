package com.zerdasoftware.thebooks.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.zerdasoftware.thebooks.model.Books
import com.zerdasoftware.thebooks.service.BookDatabase
import kotlinx.coroutines.launch

class BookDetailViewModel(application: Application): BaseViewModel(application){
    val BookLiveData = MutableLiveData<Books>()

    fun getRoomData (uuid: Int){
        launch {
            val dao = BookDatabase(getApplication()).bookDAO()
            val Book = dao.getBook(uuid)
            BookLiveData.value = Book
        }
    }

    fun sQLiteUpdate(value:Boolean,uuid:Int){
        launch {
            val dao = BookDatabase(getApplication()).bookDAO()
            dao.updateBookFavorite(value,uuid)
            println("sQLiteUpdate BookListViewModel : "+uuid)
        }
    }
}