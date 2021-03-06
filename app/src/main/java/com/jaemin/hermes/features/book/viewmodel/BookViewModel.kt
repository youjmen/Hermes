package com.jaemin.hermes.features.book.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava3.cachedIn
import com.jaemin.hermes.base.BaseViewModel
import com.jaemin.hermes.base.Event
import com.jaemin.hermes.entity.Book
import com.jaemin.hermes.data.repository.BookRepository
import com.jaemin.hermes.data.response.toEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi

class BookViewModel(private val bookRepository: BookRepository) : BaseViewModel() {

    private val _books : MutableLiveData<PagingData<Book>> = MutableLiveData()
    val books : LiveData<PagingData<Book>> get() = _books

    private val _bookName : MutableLiveData<String> = MutableLiveData()
    val bookName : LiveData<String> get() = _bookName

    private val _booksEmptyEvent : MutableLiveData<Event<Unit>> = MutableLiveData()
    val booksEmptyEvent : LiveData<Event<Unit>> get() = _booksEmptyEvent

    private val _booksErrorEvent : MutableLiveData<Event<Unit>> = MutableLiveData()
    val booksErrorEvent : LiveData<Event<Unit>> get() = _booksErrorEvent

    private val _booksLoadingEvent : MutableLiveData<Event<Unit>> = MutableLiveData()
    val booksLoadingEvent : LiveData<Event<Unit>> get() = _booksLoadingEvent


    @OptIn(ExperimentalCoroutinesApi::class)
    fun searchBooks(bookName : String){
        _booksLoadingEvent.value = Event(Unit)
        _bookName.value = bookName

        addDisposable(bookRepository.searchBooksWithPaging(bookName)
            .cachedIn(viewModelScope)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _books.value = response.map { it.toEntity() }
            },{
                _booksErrorEvent.value = Event(Unit)
            }))
    }
}