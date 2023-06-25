package com.example.freshegokidcompose.features.search.viewmodel

import com.example.freshegokidcompose.data.model.search.SearchQuery

sealed class SearchQueryUserAction {
    data class SaveQuery(val searchQuery: SearchQuery): SearchQueryUserAction()
    data class SelectQuery(val searchQuery: SearchQuery): SearchQueryUserAction()
    data class DeleteQuery(val searchQuery: SearchQuery): SearchQueryUserAction()
}