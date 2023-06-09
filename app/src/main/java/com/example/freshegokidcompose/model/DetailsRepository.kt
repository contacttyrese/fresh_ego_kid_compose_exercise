package com.example.freshegokidcompose.model

import android.util.Log
import com.example.freshegokidcompose.network.ProductDetailsService
import io.reactivex.Observable
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val detailsService: ProductDetailsService
) {
    fun fetchDetailsByDetailsUrl(detailsUrl: String): Observable<ProductDetailsPage> {
        return when (detailsUrl.isNotBlank()) {
            true -> {
                Log.i("repo_url_valid", "details url is valid")
                detailsService.getProductDetailsPageWithProductDetailsByPath(detailsUrl)
            }
            false -> {
                Log.e("repo_url_invalid", "details url is blank returning empty details")
                Observable.just(ProductDetailsPage())
            }
        }
    }
}