package com.jaemin.hermes.main.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jaemin.hermes.base.BaseViewModel
import com.jaemin.hermes.entity.Book
import com.jaemin.hermes.entity.Place
import com.jaemin.hermes.repository.BookRepository
import com.jaemin.hermes.repository.LocationRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(private val locationRepository: LocationRepository, private val bookRepository: BookRepository) : BaseViewModel() {

    private val _currentLocation: MutableLiveData<Place> = MutableLiveData()
    val currentLocation: MutableLiveData<Place> get() = _currentLocation

    private val _bestSellers: MutableLiveData<List<Book>> = MutableLiveData()
    val bestSellers: MutableLiveData<List<Book>> get() = _bestSellers

    private val _newBooks: MutableLiveData<List<Book>> = MutableLiveData()
    val newBooks: MutableLiveData<List<Book>> get() = _newBooks

    private val _newSpecialBooks: MutableLiveData<List<Book>> = MutableLiveData()
    val newSpecialBooks: MutableLiveData<List<Book>> get() = _newSpecialBooks

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
    fun getBestSellers(){
        addDisposable(bookRepository.getBestSellers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _bestSellers.value = it
            }, {
                it.printStackTrace()
            }))
    }
    fun getNewBooks(){
        addDisposable(bookRepository.getNewBooks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _newBooks.value = it
            }, {
                it.printStackTrace()
            }))
    }
    fun getNewSpecialBooks(){
        addDisposable(bookRepository.getNewSpecialBooks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _newSpecialBooks.value = it
            }, {
                it.printStackTrace()
            }))
    }
}