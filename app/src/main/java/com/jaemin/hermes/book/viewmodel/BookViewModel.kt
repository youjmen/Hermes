package com.jaemin.hermes.book.viewmodel

import androidx.lifecycle.ViewModel
import com.jaemin.hermes.repository.BookRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class BookViewModel(private val bookRepository: BookRepository) : ViewModel() {

    fun searchBooks(bookName : String){
        bookRepository.searchBooks(bookName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            },{})
    }
}