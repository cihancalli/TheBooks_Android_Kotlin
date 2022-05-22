package com.zerdasoftware.thebooks.model

import com.google.gson.annotations.SerializedName

//Result datanın içinde books adında liste gelir
class ResultsData (
    @SerializedName("books")
    val books: List<Books>) {
}