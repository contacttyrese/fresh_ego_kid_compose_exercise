package com.example.freshegokidcompose.data.repository.home

import com.example.freshegokidcompose.data.model.home.HomePage
import com.example.freshegokidcompose.data.remote.home.HomeService
import io.reactivex.Observable
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val homeService: HomeService
) {
    fun fetchHomeBannerAndHomeSearchResults(): Observable<HomePage> {
        return homeService.getHomepage()
    }
}