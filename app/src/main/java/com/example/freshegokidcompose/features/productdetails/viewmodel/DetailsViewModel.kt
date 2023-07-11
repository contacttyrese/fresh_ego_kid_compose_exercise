package com.example.freshegokidcompose.features.productdetails.viewmodel

import androidx.lifecycle.ViewModel
import com.example.freshegokidcompose.domain.DetailsInteractor
import com.example.freshegokidcompose.data.model.productdetails.ProductDetailsPage
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val interactor: DetailsInteractor
) : ViewModel() {
    private lateinit var _detailsUrl: String

    fun setRequiredProductDetails(detailsUrl: String) {
        _detailsUrl = detailsUrl
    }

    fun getProductDetails(): Observable<ProductDetailsPage> {
        return interactor.getPageObservableWithDetailsByDetailsUrl(_detailsUrl)
    }

}