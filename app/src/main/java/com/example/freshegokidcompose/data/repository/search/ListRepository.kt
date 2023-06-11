package com.example.freshegokidcompose.data.repository.search

import com.example.freshegokidcompose.data.model.search.ProductListPage
import com.example.freshegokidcompose.helpers.RepositoryHelper
import com.example.freshegokidcompose.data.remote.search.ProductListService
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