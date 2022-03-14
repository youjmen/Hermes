package com.jaemin.hermes.features.book.view.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookUiModel(val title : String,
                       val author : String,
                       val description : String,
                       val cover : String,
                       val price : Int,
                       val isbn : String) : Parcelable

