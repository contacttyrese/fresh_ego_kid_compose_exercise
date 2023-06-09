package com.example.freshegokidcompose.viewmodel

sealed class DetailsUserAction {
    object AwaitingSelection : DetailsUserAction()
    object AddToCart : DetailsUserAction()
}