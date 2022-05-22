package com.zerdasoftware.thebooks.service


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.zerdasoftware.thebooks.model.Books


@Dao
interface BookDAO {

    @Insert
    suspend fun insertAll(vararg book: Books) : List<Long>

    @Query("SELECT * FROM Books ")
    suspend fun getAllBook() : List<Books>

    @Query("SELECT * FROM Books WHERE uuid = :bookID")
    suspend fun getBook(bookID:Int) : Books

    @Query("DELETE FROM Books ")
    suspend fun deleteALLBook()

    @Query("UPDATE Books SET book_favorite=:value WHERE uuid = :bookID")
    suspend fun updateBookFavorite(value:Boolean, bookID: Int)

    @Query("UPDATE Books SET title=:title,author=:author,book_image=:book_image,book_favorite=:book_favorite  WHERE primary_isbn10 = :primary_isbn10")
    suspend fun updateBook(title:String,author:String,book_image:String,book_favorite:Boolean, primary_isbn10:String)
}