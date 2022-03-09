package com.jaemin.hermes.datasource.remote

import android.util.Log
import com.jaemin.hermes.entity.Bookstore
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jsoup.Jsoup

class KyoboBooksScraper {
    fun scrapBookStock(isbn: String, bookstores: List<Bookstore>): Single<List<Bookstore>> {
        return Single.just(isbn)
            .map {
                val url =
                    "https://www.kyobobook.co.kr/prom/2013/general/StoreStockTable.jsp?barcode=$isbn&ejkgb=KOR"
                val document = Jsoup.connect(url).get()
                document
            }
            .subscribeOn(Schedulers.io())
            .map {

                val stocks = it.select("a[href]").map { it.text() }

                val stores = it.select("th")
                    .filter {
                        !it.text().isNullOrEmpty()
                    }.map { it.text().replace("ㆍ", "") }
                val distinguishedBookstores = distinguishKyoboBooks(bookstores)
                distinguishedBookstores.first.forEachIndexed { index, bookstore ->

                    stores.filterIndexed { idx, storeName ->
                        if (bookstore.name.contains(storeName)) {
                            bookstores[distinguishedBookstores.second[index]].bookStock = stocks[idx]
                            true
                        } else {
                            false
                        }
                    }

                }
                bookstores
            }
            .subscribeOn(Schedulers.computation())

    }
    private fun distinguishKyoboBooks(bookstores: List<Bookstore>) : Pair<List<Bookstore>, List<Int>> {
        val indexList = mutableListOf<Int>()

        val filteredBookstores = bookstores.filter {
                bookstore -> bookstore.name.contains("교보문고") }

        filteredBookstores.forEach {
            indexList.add(bookstores.indexOf(it))
        }

        return Pair(filteredBookstores, indexList)

    }

}