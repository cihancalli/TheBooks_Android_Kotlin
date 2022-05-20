package com.zerdasoftware.thebooks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zerdasoftware.thebooks.R
import com.zerdasoftware.thebooks.model.Books
import com.zerdasoftware.thebooks.model.ObjectData
import kotlinx.android.synthetic.main.book_recycler_row.view.*

class BookRecyclerAdapter(val booksList : ArrayList<Books>) : RecyclerView.Adapter<BookRecyclerAdapter.BookViewHolder>() {
    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.book_recycler_row,parent,false)
        return BookViewHolder(view)
    }

    override fun getItemCount(): Int {
        return booksList.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
           holder.itemView.textViewBookTitle.text = booksList.get(position).title
           holder.itemView.textViewBookAuthor.text = booksList.get(position).author


        holder.itemView.imageViewBookFavorite.setOnClickListener {
            if (booksList.get(position).book_favorite == true){
                holder.itemView.imageViewBookFavorite.setImageResource(R.drawable.unfavorite_button)
                booksList.get(position).book_favorite = false
            }else{
                booksList.get(position).book_favorite = true
                holder.itemView.imageViewBookFavorite.setImageResource(R.drawable.favorite_button)
            }
        }
    }

    fun BookListUpdate(newBookList: List<Books>){
        booksList.clear()
        booksList.addAll(newBookList)
        notifyDataSetChanged()
    }
}