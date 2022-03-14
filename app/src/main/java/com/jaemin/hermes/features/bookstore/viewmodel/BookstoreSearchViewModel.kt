package com.jaemin.hermes.features.bookstore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jaemin.hermes.base.BaseViewModel
import com.jaemin.hermes.base.Event
import com.jaemin.hermes.entity.Bookstore
import com.jaemin.hermes.entity.Place
import com.jaemin.hermes.data.exception.EmptyPlaceException
import com.jaemin.hermes.data.repository.LocationRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class BookstoreSearchViewModel(
    private val locationRepository: LocationRepository,
) : BaseViewModel() {

    val currentPlace: MutableLiveData<Place> = MutableLiveData()

    private val _emptySavedLocationEvent: MutableLiveData<Event<Unit>> = MutableLiveData()
    val emptySavedLocationEvent: LiveData<Event<Unit>> get() = _emptySavedLocationEvent

    private val _bookstores: MutableLiveData<List<Bookstore>> = MutableLiveData()
    val bookstores: LiveData<List<Bookstore>> get() = _bookstores

    fun searchCurrentLocationPlace(longitude: Double, latitude: Double) {
        addDisposable(
            locationRepository.searchPlaceByAddress(longitude, latitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    currentPlace.value = it

                }, {
                    when (it) {
                        is EmptyPlaceException -> {

                        }
                    }
                })
        )
    }

    fun getCurrentLocation() {
        addDisposable(
            locationRepository.getCurrentLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    currentPlace.value = it
                }, {
                    _emptySavedLocationEvent.value = Event(Unit)
                    it.printStackTrace()
                })
        )

    }

    fun searchBookstoreByAddressWithRadius(longitude: Double, latitude: Double, radius: Int) {
        addDisposable(
            locationRepository.searchBookstoreByAddressWithRadius(longitude, latitude, radius)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _bookstores.value = it
                }, {
                    _emptySavedLocationEvent.value = Event(Unit)
                    it.printStackTrace()
                })
        )

    }


}