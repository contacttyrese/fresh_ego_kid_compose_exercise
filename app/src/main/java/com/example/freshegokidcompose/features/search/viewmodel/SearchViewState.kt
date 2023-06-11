package com.example.freshegokidcompose.features.search.viewmodel

import com.example.freshegokidcompose.data.model.search.ProductListPage

sealed class SearchViewState {
    object Loading: SearchViewState()
    object SetupLoading: SearchViewState()
    data class ProductLoaded(val page: ProductListPage): SearchViewState()
    data class ProductLoadError(val throwable: Throwable): SearchViewState()
}