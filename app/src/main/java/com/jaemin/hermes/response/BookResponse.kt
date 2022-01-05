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
                        val isbn : String
                        )
fun BookResponse.toEntity() : Book =
    Book(title, author, description, cover, price, isbn)