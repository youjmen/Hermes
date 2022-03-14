package com.jaemin.hermes.features.bookstore

import com.jaemin.hermes.base.BaseTest
import com.jaemin.hermes.features.bookstore.viewmodel.BookstoreSearchViewModel
import com.jaemin.hermes.entity.Bookstore
import com.jaemin.hermes.data.repository.LocationRepository
import io.reactivex.rxjava3.core.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class BookStoreSearchViewModelTest : BaseTest() {

    private lateinit var bookstoreSearchViewModel: BookstoreSearchViewModel

    private val locationRepository: LocationRepository = mock()

    @Before
    override fun before() {
        super.before()
        bookstoreSearchViewModel = BookstoreSearchViewModel(locationRepository)
    }

    @Test
    fun `(Given) 서점 검색 화면 (When) 진입 시 (Then) 주변 서점 탐색`() {
        val bookstores = listOf(Bookstore("","",0.0,0.0,"",""))

        whenever(locationRepository.searchBookstoreByAddressWithRadius(0.0,0.0 ,10)).thenReturn(
            Single.just(bookstores))

        verify(locationRepository).searchBookstoreByAddressWithRadius(0.0,0.0 ,10)


        bookstoreSearchViewModel.searchBookstoreByAddressWithRadius(0.0,0.0 ,10)
        Assert.assertEquals(bookstoreSearchViewModel.bookstores.getOrAwaitValue(), listOf(Bookstore("","",0.0,0.0,"","")))

    }

}