package com.jaemin.hermes.book.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaemin.hermes.entity.Book
import com.jaemin.hermes.repository.BookRepository
import com.jaemin.hermes.response.toEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class BookViewModel(private val bookRepository: BookRepository) : ViewModel() {

    private val _books : MutableLiveData<List<Book>> = MutableLiveData()
    val books : LiveData<List<Book>> get() = _books

    fun searchBooks(bookName : String){
        bookRepository.searchBooks(bookName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _books.value = it.item.map { response -> response.toEntity() }
            },{
                it.printStackTrace()
            })
    }
}