package com.jaemin.hermes.book.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jaemin.hermes.base.BaseViewModel
import com.jaemin.hermes.base.Event
import com.jaemin.hermes.entity.Book
import com.jaemin.hermes.repository.BookRepository
import com.jaemin.hermes.response.toEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class BookViewModel(private val bookRepository: BookRepository) : BaseViewModel() {

    private val _books : MutableLiveData<List<Book>> = MutableLiveData()
    val books : LiveData<List<Book>> get() = _books

    private val _bookName : MutableLiveData<String> = MutableLiveData()
    val bookName : LiveData<String> get() = _bookName

    private val _booksEmptyEvent : MutableLiveData<Event<Unit>> = MutableLiveData()
    val booksEmptyEvent : LiveData<Event<Unit>> get() = _booksEmptyEvent

    private val _booksErrorEvent : MutableLiveData<Event<Unit>> = MutableLiveData()
    val booksErrorEvent : LiveData<Event<Unit>> get() = _booksErrorEvent

    private val _booksLoadingEvent : MutableLiveData<Event<Unit>> = MutableLiveData()
    val booksLoadingEvent : LiveData<Event<Unit>> get() = _booksLoadingEvent

    fun searchBooks(bookName : String){
        _booksLoadingEvent.value = Event(Unit)
        _bookName.value = bookName
        addDisposable(bookRepository.searchBooks(bookName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _books.value = it
                if (it.isEmpty()){
                    _booksEmptyEvent.value = Event(Unit)
                }
            },{
                _booksErrorEvent.value = Event(Unit)
            }))
    }
}