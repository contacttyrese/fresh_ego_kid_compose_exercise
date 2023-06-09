package com.example.freshegokidcompose.features.home.viewmodel

import androidx.lifecycle.ViewModel
import com.example.freshegokidcompose.data.model.home.HomePage
import com.example.freshegokidcompose.domain.HomeInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val interactor: HomeInteractor
) : ViewModel() {
    fun getHomeBannerAndHomeSearchResults() : Observable<HomePage> {
        return interactor.prepareHomeBannerAndHomeSearchResults()
    }
}