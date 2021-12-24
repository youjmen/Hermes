package com.jaemin.hermes.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaemin.hermes.base.BaseViewModel
import com.jaemin.hermes.entity.Place
import com.jaemin.hermes.repository.LocationRepository
import com.jaemin.hermes.response.toEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class LocationRegisterViewModel(private val locationRepository: LocationRepository) : BaseViewModel() {

    val location: MutableLiveData<String> = MutableLiveData()

    private val _places: MutableLiveData<List<Place>> = MutableLiveData()
    val places: LiveData<List<Place>> get() = _places

    fun searchPlaces() {
        location.value?.let { it ->
            addDisposable(locationRepository.searchPlaces(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response->
                    _places.value = response.places.map { place ->
                        place.toEntity()
                    }
                }, {}))
        }
    }
}