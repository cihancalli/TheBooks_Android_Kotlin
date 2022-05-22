package com.zerdasoftware.thebooks.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.zerdasoftware.thebooks.R
import com.zerdasoftware.thebooks.model.Books
import com.zerdasoftware.thebooks.model.ObjectData
import com.zerdasoftware.thebooks.service.BookDatabase
import com.zerdasoftware.thebooks.util.CreatePlaceholder
import com.zerdasoftware.thebooks.util.fetchImage
import com.zerdasoftware.thebooks.view.BookListFragment
import com.zerdasoftware.thebooks.view.BookListFragmentDirections
import com.zerdasoftware.thebooks.viewmodel.BookListViewModel
import kotlinx.android.synthetic.main.book_recycler_row.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class BookRecyclerAdapter(val booksList : ArrayList<Books>) : RecyclerView.Adapter<BookRecyclerAdapter.BookViewHolder>(),
    CoroutineScope {
    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }
    private lateinit var context:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        //Adapter'in ilgili layout ile bağlantısı yapılır
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.book_recycler_row,parent,false)
        return BookViewHolder(view)
    }

    override fun getItemCount(): Int {
        //listenin sayısına göre ekranda row oluşturur
        return booksList.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.itemView.textView_row_book_title.text = booksList.get(position).title
        holder.itemView.textView_row_book_author.text = booksList.get(position).author
        var book_favorite:Boolean = booksList.get(position).book_favorite ?: false
        //Favori butonuna tıklama

        holder.itemView.imageView_row_book_favorite.setOnClickListener {
            //Daha önceden favariye alınmışsa favoriden kaldırır


            if (book_favorite == true){
                book_favorite = false
                saveSQLiteFavorite(book_favorite,booksList.get(position).uuid,holder)

            }else{
                book_favorite = true
                saveSQLiteFavorite(book_favorite,booksList.get(position).uuid,holder)
            }
            FovoriteImage(book_favorite,holder)
        }
        FovoriteImage(book_favorite,holder)
        booksList.get(position).book_favorite = book_favorite

        //hangi row'a tıklarsak o row'un detalyar sayfasına gider
        holder.itemView.setOnClickListener {
            val id = booksList.get(position).uuid
            val action = BookListFragmentDirections.actionBookListFragmentToBookDetailFragment(id,book_favorite)
            Navigation.findNavController(it).navigate(action)
        }

        //API'den görselleri çekip ilgili row ekleme
        booksList.get(position).book_image?.let {
            holder.itemView.imageView_row_book_image.fetchImage(
                it,CreatePlaceholder(holder.itemView.context))
        }
    }

    private fun saveSQLiteFavorite(bookFavorite: Boolean, uuid: Int,holder: BookViewHolder) {
        context = holder.itemView.context
        launch {
            val dao = BookDatabase(context).bookDAO()
            dao.updateBookFavorite(bookFavorite,uuid)
        }
    }

    fun FovoriteImage(book_favorite:Boolean,holder: BookViewHolder){
        if (book_favorite == true){
            holder.itemView.imageView_row_book_favorite.setImageResource(R.drawable.favorite_button)
        }else{
            holder.itemView.imageView_row_book_favorite.setImageResource(R.drawable.unfavorite_button)
        }
    }

    //Eski listeyi siler ve yeni listeyi ekler
    fun BookListUpdate(newBookList: List<Books>){
        booksList.clear()
        booksList.addAll(newBookList)
        notifyDataSetChanged()
    }

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
}