package com.example.freshegokidcompose.model

import com.example.freshegokidcompose.data.model.home.SearchResult
import pl.droidsonroids.jspoon.annotation.Selector

class ProductListPage {
    @Selector(".grid-product__content")
    var searchResults = listOf<SearchResult>()
}