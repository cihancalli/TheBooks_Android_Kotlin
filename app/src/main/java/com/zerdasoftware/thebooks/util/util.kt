package com.zerdasoftware.thebooks.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zerdasoftware.thebooks.R
import kotlinx.android.synthetic.main.book_recycler_row.view.*

//API üzerindeki linkten resimlerin çekilmesi resimler yüklenen kadar Placeholder gösterilmesi
fun ImageView.fetchImage(url:String, placeholder: CircularProgressDrawable){
    val options = RequestOptions().placeholder(placeholder).error(R.mipmap.ic_launcher_round)
    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}

fun CreatePlaceholder(context: Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

@BindingAdapter("android:downloadImage")
fun downloadImage(view: ImageView, url:String?){
    url?.let { view.fetchImage(it, CreatePlaceholder(view.context)) }
}

@BindingAdapter("android:favoriteImage")
fun favoriteImage(view: ImageView,value:Boolean){
    if (value){
        view.setImageResource(R.drawable.favorite_button)
    }else{
        view.setImageResource(R.drawable.unfavorite_button)
    }
}