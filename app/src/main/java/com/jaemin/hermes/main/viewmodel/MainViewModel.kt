package com.jaemin.hermes.main.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jaemin.hermes.base.BaseViewModel
import com.jaemin.hermes.entity.Place
import com.jaemin.hermes.repository.LocationRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(private val locationRepository: LocationRepository) : BaseViewModel() {

    private val _currentLocation: MutableLiveData<Place> = MutableLiveData()
    val currentLocation: MutableLiveData<Place> get() = _currentLocation
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
}