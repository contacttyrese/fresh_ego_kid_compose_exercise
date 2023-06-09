package com.example.freshegokidcompose.viewmodel

import com.example.freshegokidcompose.model.ProductDetailsPage

sealed class DetailsViewState {
    object Loading : DetailsViewState()
    data class LoadingError(val throwable: Throwable) : DetailsViewState()
    data class DetailsLoaded(val page: ProductDetailsPage) : DetailsViewState()
}