package com.example.freshegokidcompose.data.model.search

import pl.droidsonroids.jspoon.annotation.Selector

class ProductListPage {
    @Selector(".grid-product__content")
    var searchResults = listOf<SearchResult>()
}