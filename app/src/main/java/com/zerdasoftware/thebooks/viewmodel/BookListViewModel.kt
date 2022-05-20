package com.zerdasoftware.thebooks.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zerdasoftware.thebooks.model.Books
import com.zerdasoftware.thebooks.model.ObjectData
import com.zerdasoftware.thebooks.service.BookAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class BookListViewModel : ViewModel() {
    val Books = MutableLiveData<List<Books>>()
    val BookErrorMessage = MutableLiveData<Boolean>()
    val BookLoading = MutableLiveData<Boolean>()

    private val BookAPIService = BookAPIService()
    private val disposable = CompositeDisposable()


    fun refreshData(){
        fetchDataFromAPI()
    }

    private fun fetchDataFromAPI(){
        BookLoading.value = true
        disposable.add(
            BookAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ObjectData>() {
                    override fun onSuccess(t: ObjectData) {
                        //Başarılı Olursa
                        Books.value = t.results.books
                        BookErrorMessage.value = false
                        BookLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        //Hata alırsak
                        BookErrorMessage.value = true
                        BookLoading.value = false
                        e.printStackTrace()
                        println("ömer hata "+ e)
                    }

                })
        )
    }
}