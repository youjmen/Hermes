package com.jaemin.hermes.data.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.observable
import com.jaemin.hermes.BuildConfig
import com.jaemin.hermes.data.datasource.remote.*
import com.jaemin.hermes.entity.Bookstore
import com.jaemin.hermes.data.response.BookResponse
import com.jaemin.hermes.data.response.BooksResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class BookDataSourceImpl(
    private val bookService: BookService,
    private val kyoboBooksScraper: KyoboBooksScraper,
) : BookDataSource {

    override fun searchBooks(bookName: String): Single<BooksResponse> =
        bookService.searchBooks(BuildConfig.TTB_KEY, bookName)


    override fun searchBooksWithPaging(bookName: String): Observable<PagingData<BookResponse>> {
        return Pager(PagingConfig(20, prefetchDistance = 1,enablePlaceholders = false), pagingSourceFactory = { BookPagingSource(bookService, bookName) })
            .observable
    }

    override fun getBookInformation(isbn: String): Single<BooksResponse> {
        return if (isbn.length == 13) {
            bookService.getBookInformation(BuildConfig.TTB_KEY, isbn)
        } else {
            bookService.getBookInformationWithISBN(BuildConfig.TTB_KEY, isbn)
        }
    }

    override fun getKyoboBookStocks(isbn: String, bookstores: List<Bookstore>): Single<List<Bookstore>> {
        return kyoboBooksScraper.scrapBookStock(isbn, bookstores)
    }

    override fun getBestSellers(): Single<BooksResponse> =
        bookService.getBestSellers(BuildConfig.TTB_KEY)

    override fun getBestSellersWithPaging(): Observable<PagingData<BookResponse>> {
        return Pager(PagingConfig(20), pagingSourceFactory = { BestSellerPagingSource(bookService) })
            .observable
    }

    override fun getNewSpecialBooks(): Single<BooksResponse> =
        bookService.getNewSpecialBooks(BuildConfig.TTB_KEY)

    override fun getNewSpecialBooksWithPaging(): Observable<PagingData<BookResponse>> {
        return Pager(PagingConfig(20), pagingSourceFactory = { NewSpecialBooksPagingSource(bookService) })
            .observable    }

    override fun getNewBooks(): Single<BooksResponse> =
        bookService.getNewBooks(BuildConfig.TTB_KEY)

    override fun getNewBooksWithPaging(): Observable<PagingData<BookResponse>> {
        return Pager(PagingConfig(20), pagingSourceFactory = { NewBooksPagingSource(bookService) })
            .observable
    }


}