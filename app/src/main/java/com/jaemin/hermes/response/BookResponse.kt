package com.jaemin.hermes.response

import com.jaemin.hermes.entity.Book

data class BookResponse(val title : String,
                        val author : String,
                        val pubDate : String,
                        val description : String,
                        val cover : String
                        )
fun BookResponse.toEntity() : Book =
    Book(title, author, pubDate, description, cover)