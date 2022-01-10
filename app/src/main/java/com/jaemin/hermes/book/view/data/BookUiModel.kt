package com.jaemin.hermes.book.view.data

import android.os.Parcelable
import com.jaemin.hermes.entity.Book
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookUiModel(val title : String,
                       val author : String,
                       val description : String,
                       val cover : String,
                       val price : Int,
                       val isbn : String) : Parcelable

fun Book.toUiModel() : BookUiModel =
    BookUiModel(title, author, description, cover, price, isbn)
