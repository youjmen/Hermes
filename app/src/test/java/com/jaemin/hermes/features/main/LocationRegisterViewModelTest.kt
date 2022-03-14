package com.jaemin.hermes.features.main

import com.jaemin.hermes.base.BaseTest
import com.jaemin.hermes.base.Event
import com.jaemin.hermes.entity.Place
import com.jaemin.hermes.features.main.viewmodel.LocationRegisterViewModel
import com.jaemin.hermes.data.repository.LocationRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class LocationRegisterViewModelTest : BaseTest() {

    private lateinit var locationRegisterViewModel: LocationRegisterViewModel


    private val locationRepository : LocationRepository = mock()

    @Before
    override fun before() {
        super.before()

        locationRegisterViewModel = LocationRegisterViewModel(locationRepository)
    }

    @Test
    fun `(Given) 장소 이름 (When) 입력 시 (Then) 장소 목록 탐색`(){
        whenever(locationRepository.searchPlaces("신세계백화점")).thenReturn(Single.just(listOf(Place("신세계백화점","",0.0,0.0,""))))
        locationRegisterViewModel.location.value = "신세계백화점"
        locationRegisterViewModel.searchPlaces()
        verify(locationRepository).searchPlaces("신세계백화점")
        Assert.assertEquals(locationRegisterViewModel.places.getOrAwaitValue(),listOf(Place("신세계백화점","",0.0,0.0,"")))
    }
    @Test
    fun `(Given) 현재 좌표 (When) 입력 시 (Then) 현재 위치 장소 탐색`(){
        whenever(locationRepository.searchPlaceByAddress(0.0,0.0)).thenReturn(Single.just(Place("신세계백화점","",0.0,0.0,"")))

        locationRegisterViewModel.searchCurrentLocationPlace(0.0,0.0)
        verify(locationRepository).searchPlaceByAddress(0.0,0.0)

        Assert.assertEquals(locationRegisterViewModel.currentPlace.getOrAwaitValue(),Place("신세계백화점","",0.0,0.0,""))
    }
    @Test
    fun `(Given) 위치 등록 화면 (When) 진입 시 (Then) 현재 위치 조회`(){
        whenever(locationRepository.getCurrentLocation()).thenReturn(Single.just(Place("신세계백화점","",0.0,0.0,"")))

        locationRegisterViewModel.getCurrentLocation()
        verify(locationRepository).getCurrentLocation()

        Assert.assertEquals(locationRegisterViewModel.currentPlace.getOrAwaitValue(),Place("신세계백화점","",0.0,0.0,""))
    }
    @Test
    fun `(Given) 현재 장소 정보 (When) 위치 등록 버튼 클릭 시 (Then) 현재 장소 저장`(){
        val place = Place("신세계백화점","",0.0,0.0,"")
        whenever(locationRepository.insertCurrentLocation(place)).thenReturn(Completable.complete())

        locationRegisterViewModel.currentPlace.value = place
        locationRegisterViewModel.saveCurrentLocation()

        verify(locationRepository).insertCurrentLocation(place)
        Assert.assertEquals(locationRegisterViewModel.saveLocationSuccessEvent.getOrAwaitValue().peekContent(), Event(Unit).peekContent())
    }

}