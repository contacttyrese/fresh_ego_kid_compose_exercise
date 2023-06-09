package com.example.freshegokidcompose.features.search.viewmodel

sealed class SearchUserAction {
    object AwaitingInput: SearchUserAction()
    data class UserTyping(val input: String): SearchUserAction()
    data class QuerySubmittedSuccess(val query: String): SearchUserAction()
    object QuerySubmittedError: SearchUserAction()
}