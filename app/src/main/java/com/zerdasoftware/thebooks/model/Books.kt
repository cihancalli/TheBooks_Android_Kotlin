package com.zerdasoftware.thebooks.model

import com.google.gson.annotations.SerializedName

class Books(
    @SerializedName("title")
    val title:String?,
    @SerializedName("author")
    val author:String?,
    @SerializedName("book_image")
    val book_image:String?,
    var book_favorite:Boolean?
    )  {
}