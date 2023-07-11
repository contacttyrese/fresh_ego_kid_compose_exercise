package com.example.freshegokidcompose.features.search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import com.example.freshegokidcompose.features.navigationbar.view.DisplayTopAppBar
import com.example.freshegokidcompose.features.search.view.DisplaySearchBar
import com.example.freshegokidcompose.features.search.view.DisplaySearchResultsFromViewState
import com.example.freshegokidcompose.ui.theme.CustomAppTheme
import com.example.freshegokidcompose.features.search.viewmodel.SearchViewModel
import com.example.freshegokidcompose.features.search.viewmodel.SearchViewState
import com.example.freshegokidcompose.features.search.viewmodel.SearchQueryState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : ComponentActivity() {
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomAppTheme {
                val viewState by remember {
                    viewModel.viewState
                }.observeAsState(SearchViewState.SetupLoading)
                val userAction = remember {
                    viewModel.userActionSubject
                }
                val queryAction = remember {
                    viewModel.queryActionSubject
                }
                val queryState by remember {
                    viewModel.searchState
                }.observeAsState(initial = SearchQueryState.SetupState)
                Column {
                    DisplayTopAppBar(
                        "Search",
                        true,
                        false
                    )
                    DisplaySearchBar(userAction, queryAction, queryState, viewModel.getSearchQueries())
                    DisplaySearchResultsFromViewState(viewState)
                }
            }
        }
    }
}