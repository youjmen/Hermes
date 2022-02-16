package com.jaemin.hermes.datasource.remote

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.jaemin.hermes.BuildConfig
import com.jaemin.hermes.response.BookResponse
import io.reactivex.rxjava3.core.Single
import java.lang.Exception

class BookPagingSource(private val bookService: BookService, private val bookName : String) : RxPagingSource<Int, BookResponse>() {
    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, BookResponse>>{
        val page = params.key ?: 1
        if (page < 1){
            return Single.just(LoadResult.Error(Exception("page under 0")))
        }
        return bookService.searchBooks(BuildConfig.TTB_KEY, bookName, page)
            .map {
                LoadResult.Page(it.item, page-1, page + 1) as LoadResult<Int, BookResponse>
            }.onErrorReturn {
                LoadResult.Error(it)
            }

    }
    override fun getRefreshKey(state: PagingState<Int, BookResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    }

}