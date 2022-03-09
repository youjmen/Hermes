package com.jaemin.hermes.main

import com.jaemin.hermes.base.BaseTest
import com.jaemin.hermes.entity.Place
import com.jaemin.hermes.main.viewmodel.MainViewModel
import com.jaemin.hermes.repository.BookRepository
import com.jaemin.hermes.repository.LocationRepository
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class MainViewModelTest : BaseTest() {

    private lateinit var mainViewModel : MainViewModel

    private val bookRepository : BookRepository = mock()

    private val locationRepository : LocationRepository = mock()

    @Before
    override fun before() {
        super.before()

        mainViewModel = MainViewModel(locationRepository, bookRepository)
    }

    @Test
    fun `(Given) 메인 화면 (When) 진입 시 (Then) 위치 정보 조회`(){
        whenever(locationRepository.getCurrentLocation()).thenReturn(Single.just(Place("","",0.0,0.0,"")))
        mainViewModel.getCurrentLocation()
        verify(locationRepository).getCurrentLocation()
        Assert.assertEquals(mainViewModel.currentLocation.getOrAwaitValue(),Place("","",0.0,0.0,""))
    }

}