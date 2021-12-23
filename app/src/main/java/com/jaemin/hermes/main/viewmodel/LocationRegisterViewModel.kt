package com.jaemin.hermes.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaemin.hermes.repository.LocationRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class LocationRegisterViewModel(private val locationRepository: LocationRepository) : ViewModel(){

    private val _location : MutableLiveData<String> = MutableLiveData("갤러리휴리움")
    private val location : LiveData<String> get() = _location

    fun searchLocation(){
        _location.value?.let {
            locationRepository.searchBuildings(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                           Log.d("locationResponse", it.toString())
                },{})
        }
    }
}