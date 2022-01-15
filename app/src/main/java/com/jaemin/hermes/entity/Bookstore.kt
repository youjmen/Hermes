package com.jaemin.hermes.entity


data class Bookstore(
    val name : String,
    val roadAddress : String,
    val latitude : Double,
    val longitude : Double,
    val phoneNumber : String,
    var bookStock : String? = null
)
