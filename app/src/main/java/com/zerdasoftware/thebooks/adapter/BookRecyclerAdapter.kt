package com.zerdasoftware.thebooks.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.zerdasoftware.thebooks.R
import com.zerdasoftware.thebooks.databinding.BookRecyclerRowBinding
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

class BookRecyclerAdapter(val booksList : ArrayList<Books>) : RecyclerView.Adapter<BookRecyclerAdapter.BookViewHolder>(),BookClickListener {

    class BookViewHolder(var view: BookRecyclerRowBinding) : RecyclerView.ViewHolder(view.root){
    }

    override fun bookTapped(view: View) {
        val uuid = view.bookID.text.toString().toIntOrNull()
        uuid?.let {
            val action = BookListFragmentDirections.actionBookListFragmentToBookDetailFragment(it)
            Navigation.findNavController(view).navigate(action)
        }
    }

    private lateinit var context:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        //Adapter'in ilgili layout ile bağlantısı yapılır
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.book_recycler_row,parent,false)
        val view = DataBindingUtil.inflate<BookRecyclerRowBinding>(inflater,R.layout.book_recycler_row,parent,false)
        return BookViewHolder(view)
    }

    override fun getItemCount(): Int {
        //listenin sayısına göre ekranda row oluşturur
        return booksList.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.view.bookRow = booksList[position]
        holder.view.listenerRow = this
    }

    //Eski listeyi siler ve yeni listeyi ekler
    fun BookListUpdate(newBookList: List<Books>){
        booksList.clear()
        booksList.addAll(newBookList)
        notifyDataSetChanged()
    }
}