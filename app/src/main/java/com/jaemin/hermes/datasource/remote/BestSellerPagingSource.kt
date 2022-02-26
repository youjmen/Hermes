package com.jaemin.hermes.datasource.remote

import android.util.Log
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.jaemin.hermes.BuildConfig
import com.jaemin.hermes.response.BookResponse
import io.reactivex.rxjava3.core.Single
import java.lang.Exception

class BestSellerPagingSource(private val bookService: BookService) : RxPagingSource<Int, BookResponse>() {
    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, BookResponse>>{
        val page = params.key ?: 1
        Log.d("okhttppages", "calls twice $page")
        if (page < 1){
            return Single.just(LoadResult.Error(Exception("page under 0")))
        }
        return bookService.getBestSellers(BuildConfig.TTB_KEY, page)
            .map {
                if (it.item.isEmpty()){
                    Log.d("dsfdsafsf","Fff")
                    LoadResult.Page(emptyList(), null, null) as LoadResult<Int, BookResponse>
                }
                else {
                    LoadResult.Page(it.item, page - 1, page + 1) as LoadResult<Int, BookResponse>
                }
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