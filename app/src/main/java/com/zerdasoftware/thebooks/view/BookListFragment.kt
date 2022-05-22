package com.zerdasoftware.thebooks.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.zerdasoftware.thebooks.R
import com.zerdasoftware.thebooks.adapter.BookRecyclerAdapter
import com.zerdasoftware.thebooks.model.ObjectData
import com.zerdasoftware.thebooks.viewmodel.BookListViewModel
import kotlinx.android.synthetic.main.fragment_book_list.*


class BookListFragment : Fragment() {

    //viewModel ve listelerin gösterileceği Adapter tanımlanıyor
    private lateinit var viewModel : BookListViewModel
    private val bookRecyclerAdapter = BookRecyclerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // viewModel initialize ediyoruz
        viewModel = ViewModelProviders.of(this).get(BookListViewModel::class.java)
        // viewModel içindeki refreshData ile internetten verilerin çekilmesi işlemi başlatılıyor
        viewModel.refreshData()

        //list layout içindeki RecyclerView tanımlanıyoruz ve adapter ile bağlantısını yapıyoruz
        recyclerViewBookList.layoutManager = LinearLayoutManager(context)
        recyclerViewBookList.adapter = bookRecyclerAdapter

        //Kullanıcı ekranı aşağı doğru kaydırınca verilerin yenilenmesini sağlıyoruz
        swipeRefreshLayout.setOnRefreshListener {
            progressBarLoadingBook.visibility = View.VISIBLE
            textViewBookErrorMessage.visibility = View.GONE
            recyclerViewBookList.visibility = View.GONE
            viewModel.refreshAPIData()
            swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()
    }


    fun observeLiveData(){
        //Çekilen dataların viewModel aracılığıyla layout içerisinde gösterilmesi
        viewModel.Books.observe(viewLifecycleOwner, Observer { books ->
            books?.let {
                recyclerViewBookList.visibility = View.VISIBLE
                bookRecyclerAdapter.BookListUpdate(books)
            }
        })

        //hata durumunda ekranda hata mesajı gösterilmesi
        viewModel.BookErrorMessage.observe(viewLifecycleOwner, Observer {error ->
            error?.let {
                if (it){
                    recyclerViewBookList.visibility = View.GONE
                    progressBarLoadingBook.visibility = View.GONE
                    textViewBookErrorMessage.visibility = View.VISIBLE
                }else{
                    textViewBookErrorMessage.visibility = View.GONE
                }
            }
        })

        //datalar yüklenirken ekranda ProgressBar gösterilmesi
        viewModel.BookLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it){
                    recyclerViewBookList.visibility = View.GONE
                    textViewBookErrorMessage.visibility = View.GONE
                    progressBarLoadingBook.visibility = View.VISIBLE
                }else{
                    progressBarLoadingBook.visibility = View.GONE
                }
            }
        })
    }
}