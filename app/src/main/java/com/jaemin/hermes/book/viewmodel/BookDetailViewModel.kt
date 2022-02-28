package com.jaemin.hermes.book.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jaemin.hermes.base.BaseViewModel
import com.jaemin.hermes.base.Event
import com.jaemin.hermes.entity.Book
import com.jaemin.hermes.repository.BookRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class BookDetailViewModel(private val bookRepository: BookRepository) : BaseViewModel() {

    private val _bookInformation: MutableLiveData<Book> = MutableLiveData()
    val bookInformation: LiveData<Book> get() = _bookInformation

    private val _bookInformationErrorEvent : MutableLiveData<Event<Unit>> = MutableLiveData()
    val bookInformationErrorEvent : LiveData<Event<Unit>> get() = _bookInformationErrorEvent

    private val _bookInformationLoadingEvent : MutableLiveData<Event<Unit>> = MutableLiveData()
    val bookInformationLoadingEvent : LiveData<Event<Unit>> get() = _bookInformationLoadingEvent

    fun getBookInformation(isbn: String) {
        _bookInformationLoadingEvent.value = Event(Unit)
        addDisposable(bookRepository.getBookInformation(isbn)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _bookInformation.value = it
            }, {
                _bookInformationErrorEvent.value = Event(Unit)
            })
        )
    }
}