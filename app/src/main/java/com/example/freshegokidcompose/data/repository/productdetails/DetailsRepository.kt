package com.example.freshegokidcompose.data.repository.productdetails

import android.util.Log
import com.example.freshegokidcompose.data.model.productdetails.ProductDetailsPage
import com.example.freshegokidcompose.data.remote.productdetails.DetailsService
import io.reactivex.Observable
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val detailsService: DetailsService
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