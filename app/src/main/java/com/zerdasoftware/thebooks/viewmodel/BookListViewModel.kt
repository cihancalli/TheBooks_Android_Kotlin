package com.zerdasoftware.thebooks.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.zerdasoftware.thebooks.model.Books
import com.zerdasoftware.thebooks.model.ObjectData
import com.zerdasoftware.thebooks.service.BookAPIService
import com.zerdasoftware.thebooks.service.BookDatabase
import com.zerdasoftware.thebooks.util.PrivateSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class BookListViewModel(application: Application) : BaseViewModel(application) {
    //API' den verileri çekip ekrana yazdırmak için BookListViewModel ile arka planda işlemleri yapıyoruz

    //çekilen datalar Books adında listeye tanımlıyoruz
    val Books = MutableLiveData<List<Books>>()
    // bir hata oluşması durumunda ekrana BookErrorMessage ile bilidirim veriyoruz
    val BookErrorMessage = MutableLiveData<Boolean>()
    //datalar çekilirken kullanıcıyı bekletme zamanında  ProgressBar gösteriyoruz
    val BookLoading = MutableLiveData<Boolean>()

    //10 dk lık nonoTime
    private var updateTime = 24 * 60 * 60 * 1000 * 1000 * 1000L

    //dataları çekmek için API servisimizi burada tanılmıyoruz
    private val BookAPIService = BookAPIService()

    // çekilen dataları disposable olarak tanımlayarak kullan at sistemi oluşturuyoruz
    private val disposable = CompositeDisposable()

    private val privateSharedPreferences = PrivateSharedPreferences(getApplication())


    //verileri tekrardan yükleme
    fun refreshData(){
        val saveTime = privateSharedPreferences.getTime()

        if (saveTime != null && saveTime != 0L && System.nanoTime() - saveTime < updateTime){
            //10 dk nın altında zaman olduğu için SQLite'dan veriyi al
            getSQLite()
        }else{
            fetchDataFromAPI()
        }
    }

    fun refreshAPIData(){
        fetchDataFromAPI()
    }

    //SQLite da kayıtlı veriyi çekme
    private fun getSQLite(){

        launch {
            val BookList = BookDatabase(getApplication()).bookDAO().getAllBook()
            showBookData(BookList)
            //Toast.makeText(getApplication(),"get Room", Toast.LENGTH_SHORT).show()
        }

    }

    //internetten verilerin çekme işlemini burada başlatıyoruz
    private fun fetchDataFromAPI(){
        //veriler çekilirken ProgressBar ekranda gözükecek
        BookLoading.value = true
        disposable.add(
            BookAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ObjectData>() {
                    override fun onSuccess(t: ObjectData) {
                        //Veri çekme işlemi Başarılı Olursa
                        sQLiteSave(t.results.books)
                        //Toast.makeText(getApplication(),"get API", Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        //Verileri çekmedeHata alırsak hata mesajı gösteriliyor
                        BookErrorMessage.value = true
                        BookLoading.value = false
                        e.printStackTrace()
                        println("Test : "+ e)
                    }

                })
        )
    }

    private fun showBookData(bookList:List<Books>){
        //Books listesine çekilen listeler aktarılıyor
        Books.value = bookList
        //hata olmadığı için hata mesajı kapatılıyor
        BookErrorMessage.value = false
        //yükleme tamamlandığı için ProgressBar kapatılıyor
        BookLoading.value = false
    }

    private fun sQLiteSave(bookList:List<Books>){
        launch {
            val dao = BookDatabase(getApplication()).bookDAO()
            //Daha önce liste varsa yani veriler çekilip SQLite kayıt edilmişse
            if (dao.getAllBook().size > 0){
                var oldBookList = dao.getAllBook()
                println("Liste VAR")
                showBookData(oldBookList)
                //Burada yeni veriler eski verilerle aynı mı diye kontrol edilir
                //yeni veriler daha önce kayıtlı değilse inser işlemi yapılır
            }
            //Veriler ilk defa çekilecekse
            else{
                println("Liste YOK")
                val uuidList = dao.insertAll(*bookList.toTypedArray())
                showBookData(bookList)
                var i = 0
                while (i < bookList.size){
                    bookList[i].uuid = uuidList[i].toInt()
                    i += 1
                }
            }
        }
        //verilerin son çekilme zamanını kaydetme
        privateSharedPreferences.saveTime(System.nanoTime())
    }

    fun sQLiteUpdate(value:Boolean,uuid:Int){
        launch {
            val dao = BookDatabase(getApplication()).bookDAO()
            dao.updateBookFavorite(value,uuid)
            println("sQLiteUpdate BookListViewModel : "+uuid)
        }
    }
}