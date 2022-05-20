package com.zerdasoftware.thebooks.model

import com.google.gson.annotations.SerializedName

class ResultsData (
    @SerializedName("books")
    val books: List<Books>) {
}