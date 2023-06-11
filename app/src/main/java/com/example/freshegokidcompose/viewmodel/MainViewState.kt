package com.example.freshegokidcompose.viewmodel

import com.example.freshegokidcompose.data.model.search.SearchResult

sealed class MainViewState {
    object LoadingBannerAndProducts : MainViewState()
    data class LoadingError(val throwable: Throwable) : MainViewState()
    data class LoadingSuccess(val bannerUrl: String, val products: List<SearchResult>) : MainViewState()
}