package com.zerdasoftware.thebooks.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.zerdasoftware.thebooks.R
import com.zerdasoftware.thebooks.databinding.FragmentBookDetailBinding
import com.zerdasoftware.thebooks.util.CreatePlaceholder
import com.zerdasoftware.thebooks.util.fetchImage
import com.zerdasoftware.thebooks.viewmodel.BookDetailViewModel
import kotlinx.android.synthetic.main.fragment_book_detail.*

class BookDetailFragment : Fragment() {

    private lateinit var viewModel : BookDetailViewModel
    private var BookID = 0
    private lateinit var dataBinding : FragmentBookDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_book_detail,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Liste sayfasından seçilen kitabın id si gönderilir ve buradan gelen id SQLite içinden çağırılır
        arguments?.let {
            BookID = BookDetailFragmentArgs.fromBundle(it).bookID
        }
        viewModel = ViewModelProviders.of(this).get(BookDetailViewModel::class.java)
        viewModel.getRoomData(BookID)

        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.BookLiveData.observe(viewLifecycleOwner, Observer { book ->
            book?.let {theBook ->
                dataBinding.bookDetail = theBook
                button_buy_now.setOnClickListener {
                    //Toast.makeText(this.context,"BUY NOW THE BOOK ON AMAZON",Toast.LENGTH_SHORT).show()
                    theBook.amazon_product_url?.let { it1 -> buyNowURL(it1) }
                }

                imageView_book_detail_favorited.setOnClickListener {

                    if (theBook.book_favorite == true){
                        favoriteImage(false,theBook.uuid)
                    }else{
                        favoriteImage(true,theBook.uuid)
                    }
                }
            }
        })
    }

    //Satın alma butonuna tıklanınca tarayıcıdan ürün url sine gider

     fun buyNowURL(url:String){
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }catch (e:Exception){

        }
    }

    fun favoriteImage(value:Boolean,id:Int){
        viewModel.sQLiteUpdate(value,id)
        viewModel.getRoomData(id)
    }
}