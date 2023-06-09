package com.example.freshegokidcompose.data.model.home

import com.example.freshegokidcompose.data.model.home.SearchResult
import pl.droidsonroids.jspoon.annotation.Selector

data class HomePage constructor(
    @Selector(".hero__image-wrapper > img", attr = "src")
    var bannerUrl: String? = null,

    @Selector(".grid-product__content")
    var products: List<SearchResult> = listOf()
)