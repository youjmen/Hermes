package com.jaemin.hermes.features.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava3.cachedIn
import com.jaemin.hermes.base.BaseViewModel
import com.jaemin.hermes.entity.Book
import com.jaemin.hermes.entity.Place
import com.jaemin.hermes.data.repository.BookRepository
import com.jaemin.hermes.data.repository.LocationRepository
import com.jaemin.hermes.data.response.toEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MainViewModel(private val locationRepository: LocationRepository, private val bookRepository: BookRepository) : BaseViewModel() {

    private val _currentLocation: MutableLiveData<Place> = MutableLiveData()
    val currentLocation: MutableLiveData<Place> get() = _currentLocation

    private val _bestSellers: MutableLiveData<PagingData<Book>> = MutableLiveData()
    val bestSellers: LiveData<PagingData<Book>> get() = _bestSellers


    private val _newBooks: MutableLiveData<PagingData<Book>> = MutableLiveData()
    val newBooks: LiveData<PagingData<Book>> get() = _newBooks

    private val _newSpecialBooks: MutableLiveData<PagingData<Book>> = MutableLiveData()
    val newSpecialBooks: LiveData<PagingData<Book>> get() = _newSpecialBooks

    fun getCurrentLocation() {
        addDisposable(locationRepository.getCurrentLocation()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _currentLocation.value = it
            }, {
                it.printStackTrace()
            })
        )

    }
    @OptIn(ExperimentalCoroutinesApi::class)
    fun getBestSellers(){
        addDisposable(bookRepository.getBestSellersWithPaging()
            .cachedIn(viewModelScope)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _bestSellers.value = it.map { it.toEntity() }
            }, {
                it.printStackTrace()
            }))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getNewBooks(){
        addDisposable(bookRepository.getNewBooksWithPaging()
            .cachedIn(viewModelScope)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _newBooks.value =  it.map { it.toEntity() }
            }, {
                it.printStackTrace()
            }))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getNewSpecialBooks(){
        addDisposable(bookRepository.getNewSpecialBooksWithPaging()
            .cachedIn(viewModelScope)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _newSpecialBooks.value =  it.map { it.toEntity() }
            }, {
                it.printStackTrace()
            }))
    }
}