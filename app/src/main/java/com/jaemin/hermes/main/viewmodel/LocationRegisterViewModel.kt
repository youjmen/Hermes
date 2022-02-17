package com.jaemin.hermes.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaemin.hermes.base.BaseViewModel
import com.jaemin.hermes.base.Event
import com.jaemin.hermes.entity.Place
import com.jaemin.hermes.exception.EmptyPlaceException
import com.jaemin.hermes.repository.LocationRepository
import com.jaemin.hermes.response.toEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.ext.getScopeName
import java.lang.Exception

class LocationRegisterViewModel(private val locationRepository: LocationRepository) :
    BaseViewModel() {

    val location: MutableLiveData<String> = MutableLiveData()

    private val _places: MutableLiveData<List<Place>> = MutableLiveData()
    val places: LiveData<List<Place>> get() = _places

    val currentPlace: MutableLiveData<Place> = MutableLiveData()

    private val _saveLocationSuccessEvent: MutableLiveData<Event<Unit>> = MutableLiveData()
    val saveLocationSuccessEvent: LiveData<Event<Unit>> get() = _saveLocationSuccessEvent

    private val _emptySavedLocationEvent: MutableLiveData<Event<Unit>> = MutableLiveData()
    val emptySavedLocationEvent: LiveData<Event<Unit>> get() = _emptySavedLocationEvent

    fun searchPlaces() {
        location.value?.let { it ->
            addDisposable(locationRepository.searchPlaces(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ responses ->
                    _places.value = responses
                }, {})
            )
        }
    }

    fun searchCurrentLocationPlace(longitude: Double, latitude: Double) {
        addDisposable(locationRepository.searchPlaceByAddress(longitude, latitude)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                currentPlace.value = it

            }, {
                when(it){
                    is EmptyPlaceException->{

                    }
                }
            })
        )
    }
    fun getCurrentLocation() {
        addDisposable(locationRepository.getCurrentLocation()
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
    fun saveCurrentLocation() {
        currentPlace.value?.let {
            addDisposable(
                locationRepository.insertCurrentLocation(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                      _saveLocationSuccessEvent.value = Event(Unit)
                    }, {
                        it.printStackTrace()
                    })
            )
        }

    }
}