package com.example.freshegokidcompose.model

import com.squareup.moshi.Json

class ProductDetailsPage {
    @field:Json(name = "product")
    var details = ProductDetails()
}