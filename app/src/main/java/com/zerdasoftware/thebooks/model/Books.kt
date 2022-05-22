package com.zerdasoftware.thebooks.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//Books listemiz buradaki modele göre çağırılır
//SQLite bu modelde kayıt olunacağını bildiriyoruz
@Entity
class Books(
    //SQLite tablosuna kayıt edilme adı
    @ColumnInfo(name = "rank")
    @SerializedName("rank")
    val rank:Int?,

    @ColumnInfo(name = "rank_last_week")
    @SerializedName("rank_last_week")
    val rank_last_week:Int?,

    @ColumnInfo(name = "weeks_on_list")
    @SerializedName("weeks_on_list")
    val weeks_on_list:Int?,

    @ColumnInfo(name = "asterisk")
    @SerializedName("asterisk")
    val asterisk:Int?,

    @ColumnInfo(name = "dagger")
    @SerializedName("dagger")
    val dagger:Int?,

    @ColumnInfo(name = "primary_isbn10")
    @SerializedName("primary_isbn10")
    var primary_isbn10:String?,

    @ColumnInfo(name = "primary_isbn13")
    @SerializedName("primary_isbn13")
    var primary_isbn13:String?,

    @ColumnInfo(name = "publisher")
    @SerializedName("publisher")
    var publisher:String?,

    @ColumnInfo(name = "description")
    @SerializedName("description")
    var description:String?,

    @ColumnInfo(name = "price")
    @SerializedName("price")
    var price:String?,

    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title:String?,

    @ColumnInfo(name = "author")
    @SerializedName("author")
    val author:String?,

    @ColumnInfo(name = "contributor")
    @SerializedName("contributor")
    val contributor:String?,

    @ColumnInfo(name = "contributor_note")
    @SerializedName("contributor_note")
    val contributor_note:String?,

    @ColumnInfo(name = "book_image")
    @SerializedName("book_image")
    val book_image:String?,

    @ColumnInfo(name = "book_image_width")
    @SerializedName("book_image_width")
    val book_image_width:Int?,

    @ColumnInfo(name = "book_image_height")
    @SerializedName("book_image_height")
    val book_image_height:Int?,

    @ColumnInfo(name = "amazon_product_url")
    @SerializedName("amazon_product_url")
    val amazon_product_url:String?,

    @ColumnInfo(name = "age_group")
    @SerializedName("age_group")
    val age_group:String?,

    @ColumnInfo(name = "book_review_link")
    @SerializedName("book_review_link")
    val book_review_link:String?,

    @ColumnInfo(name = "first_chapter_link")
    @SerializedName("first_chapter_link")
    val first_chapter_link:String?,

    @ColumnInfo(name = "sunday_review_link")
    @SerializedName("sunday_review_link")
    val sunday_review_link:String?,

    @ColumnInfo(name = "article_chapter_link")
    @SerializedName("article_chapter_link")
    val article_chapter_link:String?,

    //@SerializedName("isbns")
    //val isbns:List<BookISBN>,

    //@SerializedName("buy_links")
    //val buy_links:List<BookLink>,

    @ColumnInfo(name = "book_uri")
    @SerializedName("book_uri")
    val book_uri:String?,

    @ColumnInfo(name = "book_favorite")
    var book_favorite:Boolean?=false
    )  {

    //datalarımız birbirinden faklı şekilde SQLite kayıt etmek için PrimaryKey tanımlıyoruz
    @PrimaryKey(autoGenerate = true)
    var uuid:Int = 0

}