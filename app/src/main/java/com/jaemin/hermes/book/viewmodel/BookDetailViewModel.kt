package com.jaemin.hermes.book.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jaemin.hermes.base.BaseViewModel
import com.jaemin.hermes.entity.Book
import com.jaemin.hermes.repository.BookRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class BookDetailViewModel(private val bookRepository: BookRepository) : BaseViewModel() {

    private val _bookInformation: MutableLiveData<Book> = MutableLiveData()
    val bookInformation: LiveData<Book> get() = _bookInformation

    fun getBookInformation(isbn: String) {
        addDisposable(bookRepository.getBookInformation(isbn)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("dafaf", it.toString())
                _bookInformation.value = it

            }, {
                it.printStackTrace()
            })
        )
    }
}