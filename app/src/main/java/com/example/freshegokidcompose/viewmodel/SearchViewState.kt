package com.example.freshegokidcompose.viewmodel

import com.example.freshegokidcompose.model.ProductListPage

sealed class SearchViewState {
    object Loading: SearchViewState()
    data class ProductLoaded(val page: ProductListPage): SearchViewState()
    data class ProductLoadError(val throwable: Throwable): SearchViewState()
}