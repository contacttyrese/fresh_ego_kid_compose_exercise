package com.example.freshegokidcompose.network

import com.example.freshegokidcompose.model.ProductDetailsPage
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ProductDetailsService {
    @Headers(
        "Host: www.freshegokid.com",
        "Connection: www.freshegokid.com",
        "Upgrade-Insecure-Requests: 1",
        "User-Agent: com.freshegoproject/0.0.1 Dalvik/2.1.0 (Linux; Android 11; moto g(8) power)",
        "Accept: application/json",
        "Accept-Language: en-US,en;q=0.9"
    )

    @GET("{detailsUrl}")
    fun getProductDetailsPageWithProductDetailsByPath(@Path("detailsUrl", encoded = true) path: String):
            Observable<ProductDetailsPage>
}