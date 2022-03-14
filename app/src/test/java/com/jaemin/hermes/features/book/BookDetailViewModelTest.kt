package com.jaemin.hermes.features.book

import com.jaemin.hermes.base.BaseTest
import com.jaemin.hermes.base.Event
import com.jaemin.hermes.features.book.viewmodel.BookDetailViewModel
import com.jaemin.hermes.entity.Book
import com.jaemin.hermes.data.repository.BookRepository
import io.reactivex.rxjava3.core.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class BookDetailViewModelTest : BaseTest() {

    private lateinit var bookDetailViewModel: BookDetailViewModel

    private val bookRepository: BookRepository = mock()

    @Before
    override fun before() {
        super.before()
        bookDetailViewModel = BookDetailViewModel(bookRepository)
    }

    @Test
    fun `(Given) 책 상세 정보 화면 (When) 진입 시 (Then) 책 정보 조회`() {
        whenever(bookRepository.getBookInformation("1234")).thenReturn(
            Single.just(
                Book("test","test","test","test",1000,"1234")
            )
        )
        bookDetailViewModel.getBookInformation("1234")
        verify(bookRepository).getBookInformation("1234")
        Assert.assertEquals(bookDetailViewModel.bookInformation.getOrAwaitValue(), Book("test","test","test","test",1000,"1234"))
    }


    @Test
    fun `(Given) 책 상세 정보 화면 (When) 진입 시 네트워크 혹은 잘못된 ISBN으로 오류 발생 (Then) 책 정보 조회 실패`() {
        whenever(bookRepository.getBookInformation("1234")).thenReturn(
            Single.error(
                Exception("")
            )
        )
        bookDetailViewModel.getBookInformation("1234")
        verify(bookRepository).getBookInformation("1234")
        Assert.assertEquals(bookDetailViewModel.bookInformationErrorEvent.getOrAwaitValue().peekContent(), Event(Unit).peekContent())
    }
}