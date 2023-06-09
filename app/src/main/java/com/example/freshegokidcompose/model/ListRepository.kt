package com.example.freshegokidcompose.model

import com.example.freshegokidcompose.helpers.RepositoryHelper
import com.example.freshegokidcompose.network.ProductListService
import io.reactivex.Observable
import javax.inject.Inject

class ListRepository @Inject constructor(
    private val listService: ProductListService,
    private val helper: RepositoryHelper
) {

    fun fetchSearchResultsByQuery(query: String): Observable<ProductListPage> {
        return when (helper.checkQueryIsValid(query)) {
            true -> listService.getProductListPageWithSearchResultsByQuery(query)
            false -> Observable.just(ProductListPage())
        }
    }
}