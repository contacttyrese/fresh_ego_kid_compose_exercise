package com.example.freshegokidcompose.domain

import android.util.Log
import com.example.freshegokidcompose.data.model.home.HomePage
import com.example.freshegokidcompose.data.repository.home.HomeRepository
import com.example.freshegokidcompose.helpers.InteractorHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeInteractor @Inject constructor(
    private val repository: HomeRepository,
    private val helper: InteractorHelper
) {

    fun prepareHomeBannerAndHomeSearchResults(): Observable<HomePage> {
        return repository.fetchHomeBannerAndHomeSearchResults()
            .subscribeOn(Schedulers.io())
            .doOnError { throwable ->
                processThrowable(throwable)
            }
            .doOnNext { page ->
                page.products.forEach { searchResult ->
                    helper.refactorImageUrlsForPage(searchResult)
                }

                page.bannerUrl?.let { bannerUrl ->
                    page.bannerUrl = "https:$bannerUrl"
                    Log.i("banner_url_refactored", "image refactored url: ${page.bannerUrl}")
                } ?: kotlin.run {
                    Log.e("next_bannerurl_error", "banner url is null")
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun processThrowable(throwable: Throwable) {
        Log.e("mainviewmodel_error", "banner and products loading error: $throwable")
        throwable.printStackTrace()
    }

}