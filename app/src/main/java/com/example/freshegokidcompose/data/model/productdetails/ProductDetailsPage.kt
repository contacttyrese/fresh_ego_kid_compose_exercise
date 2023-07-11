package com.example.freshegokidcompose.data.model.productdetails

import com.squareup.moshi.Json

class ProductDetailsPage {
    @field:Json(name = "product")
    var details = ProductDetails()
}