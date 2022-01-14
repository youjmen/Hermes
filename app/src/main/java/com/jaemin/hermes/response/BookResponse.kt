package com.jaemin.hermes.response

import com.google.gson.annotations.SerializedName
import com.jaemin.hermes.entity.Book

data class BookResponse(
                        @SerializedName("title")
                        val title : String,
                        @SerializedName("author")
                        val author : String,
                        @SerializedName("description")
                        val description : String,
                        @SerializedName("cover")
                        val cover : String,
                        @SerializedName("priceStandard")
                        val price : Int,
                        @SerializedName("isbn13")
                        val isbn13 : String,
                        @SerializedName("isbn")
                        val isbn : String,
                        )
fun BookResponse.toEntity() : Book {
    return if (isbn13 == "") {
        Book(title, author, description, cover.replace("coversum", "cover500"), price, isbn)
    }
    else{
        Book(title, author, description, cover.replace("coversum", "cover500"), price, isbn13)
    }
}