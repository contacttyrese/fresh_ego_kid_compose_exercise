package com.example.freshegokidcompose.features.search.viewmodel

import com.example.freshegokidcompose.data.model.search.SearchQuery

sealed class SearchQueryState {
    object SavingQuery: SearchQueryState()
    data class SelectedQuery(val searchQuery: SearchQuery): SearchQueryState()
    data class QueryError(val throwable: Throwable): SearchQueryState()
    object DeletingQuery: SearchQueryState()
    data class GetAllQueries(val queries: List<SearchQuery>): SearchQueryState()
    object SetupState: SearchQueryState()
}