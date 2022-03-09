package com.jaemin.hermes.book

import com.jaemin.hermes.base.BaseTest
import com.jaemin.hermes.base.Event
import com.jaemin.hermes.book.viewmodel.BookDetailViewModel
import com.jaemin.hermes.book.viewmodel.CheckStockViewModel
import com.jaemin.hermes.entity.Book
import com.jaemin.hermes.entity.Bookstore
import com.jaemin.hermes.entity.Place
import com.jaemin.hermes.repository.BookRepository
import com.jaemin.hermes.repository.LocationRepository
import io.reactivex.rxjava3.core.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class CheckStockViewModelTest : BaseTest() {

    private lateinit var checkStockViewModel: CheckStockViewModel

    private val locationRepository: LocationRepository = mock()

    private val bookRepository: BookRepository = mock()

    @Before
    override fun before() {
        super.before()
        checkStockViewModel = CheckStockViewModel(locationRepository, bookRepository)
    }

    @Test
    fun `(Given) 재고 확인 화면 (When) 진입 시 (Then) 책 재고 탐색`() {
        val bookstores = listOf(Bookstore("","",0.0,0.0,"",""))

        whenever(locationRepository.searchBookstoreByAddressWithRadius(0.0,0.0 ,10)).thenReturn(
            Single.just(bookstores))

        checkStockViewModel.searchBookstoreByAddressWithRadius(0.0,0.0 ,10)

        whenever(bookRepository.getBookStocks("1234", checkStockViewModel.bookstores.getOrAwaitValue())).thenReturn(Single.just(
            listOf(Bookstore("","",0.0,0.0,"","","4"))))

        checkStockViewModel.getBookStocks("1234")

        verify(bookRepository).getBookStocks("1234", bookstores)
        Assert.assertEquals(checkStockViewModel.bookstores.getOrAwaitValue(), listOf(Bookstore("","",0.0,0.0,"","","4")))
    }

    @Test
    fun `(Given) 재고 확인 화면 (When) 진입 시 (Then) 주변 서점 탐색`() {
        val bookstores = listOf(Bookstore("","",0.0,0.0,"",""))

        whenever(locationRepository.searchBookstoreByAddressWithRadius(0.0,0.0 ,10)).thenReturn(
            Single.just(bookstores))

        checkStockViewModel.searchBookstoreByAddressWithRadius(0.0,0.0 ,10)
        Assert.assertEquals(checkStockViewModel.bookstores.getOrAwaitValue(), listOf(Bookstore("","",0.0,0.0,"","")))

    }

    @Test
    fun `(Given) 재고 확인 화면 (When) 진입 시 (Then) 저장한 위치 조회`() {
        whenever(locationRepository.getCurrentLocation()).thenReturn(Single.just(Place("신세계백화점","",0.0,0.0,"")))

        checkStockViewModel.getCurrentLocation()
        verify(locationRepository).getCurrentLocation()

        Assert.assertEquals(checkStockViewModel.currentPlace.getOrAwaitValue(),
            Place("신세계백화점","",0.0,0.0,"")
        )
    }

    @Test
    fun `(Given) 재고 확인 화면 (When) 진입 시 (Then) 현재 위치 정보 탐색`() {
        whenever(locationRepository.searchPlaceByAddress(0.0,0.0)).thenReturn(Single.just(Place("신세계백화점","",0.0,0.0,"")))

        checkStockViewModel.searchCurrentLocationPlace(0.0,0.0)
        verify(locationRepository).searchPlaceByAddress(0.0,0.0)

        Assert.assertEquals(checkStockViewModel.currentPlace.getOrAwaitValue(),
            Place("신세계백화점","",0.0,0.0,"")
        )

    }






}