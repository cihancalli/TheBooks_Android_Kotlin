package com.zerdasoftware.thebooks.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.zerdasoftware.thebooks.R
import com.zerdasoftware.thebooks.util.CreatePlaceholder
import com.zerdasoftware.thebooks.util.fetchImage
import com.zerdasoftware.thebooks.viewmodel.BookDetailViewModel
import kotlinx.android.synthetic.main.fragment_book_detail.*

class BookDetailFragment : Fragment() {

    private lateinit var viewModel : BookDetailViewModel
    private var BookID = 0
    private var book_favorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        arguments?.let {
            BookID = BookDetailFragmentArgs.fromBundle(it).bookID
            book_favorite = BookDetailFragmentArgs.fromBundle(it).bookFavorite

        }
        viewModel = ViewModelProviders.of(this).get(BookDetailViewModel::class.java)
        viewModel.getRoomData(BookID)

        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.BookLiveData.observe(viewLifecycleOwner, Observer { book ->
            book?.let {bookData ->
                textView_book_detail_title.text = bookData.title
                textView_book_detail_author.text = bookData.author
                textView_book_detail_publisher.text = bookData.publisher
                textView_book_detail_description.text = bookData.description
                textView_book_detail_ısbn10.text = bookData.primary_isbn10
                textView_book_detail_ısbn13.text = bookData.primary_isbn13
                button_buy_now.setOnClickListener {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(bookData.amazon_product_url))
                        startActivity(intent)
                    }catch (e:Exception){

                    }
                }
                //viewModel.sQLiteUpdate(book_favorite,it.uuid)
                context?.let {
                    imageView_book_detail.fetchImage(book.book_image.toString(),CreatePlaceholder(it))
                }
                println("BookDetailFragment book_favorite  primary_isbn10 : "+bookData.primary_isbn10)


                if (bookData.book_favorite==true){
                    imageView_book_detail_favorited.setImageResource(R.drawable.favorite_button)
                    println("BookDetailFragment book_favorite TRUE : "+bookData.uuid)

                    println("Status "+book_favorite)
                }else{
                    imageView_book_detail_favorited.setImageResource(R.drawable.unfavorite_button)
                    println("BookDetailFragment book_favorite FALSE : "+bookData.uuid)
                    println("Status "+book_favorite)
                }
            }
        })
    }

}