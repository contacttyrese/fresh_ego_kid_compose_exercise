package com.example.freshegokidcompose.features.search.view

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.rxjava2.subscribeAsState
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.freshegokidcompose.data.model.search.SearchResult
import com.example.freshegokidcompose.data.model.search.ProductListPage
import com.example.freshegokidcompose.ui.theme.CustomAppTheme
import com.example.freshegokidcompose.features.search.viewmodel.SearchUserAction
import com.example.freshegokidcompose.features.search.viewmodel.SearchViewState
import com.example.freshegokidcompose.data.model.search.SearchQuery
import com.example.freshegokidcompose.features.search.viewmodel.SearchQueryState
import com.example.freshegokidcompose.features.search.viewmodel.SearchQueryUserAction
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun DisplaySearchBar(userAction: PublishSubject<SearchUserAction>,
                     queryAction: PublishSubject<SearchQueryUserAction>,
                     queryState: SearchQueryState,
                     searchQueriesObservable: Observable<List<SearchQuery>>
                     ) {
    var text by rememberSaveable {
        mutableStateOf("")
    }
    var isActive by rememberSaveable {
        mutableStateOf(false)
    }
    var recentText by rememberSaveable {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchQueries by remember {
        searchQueriesObservable
    }.subscribeAsState(initial = listOf())
    recentText = UpdateRecentTextWithQueryState(queryState, userAction)
    SearchBar(
        query = when (recentText.isNotBlank()) {
            true -> {
                text = recentText
                recentText = ""
                text
            }
            false -> text
        },
        shape = SearchBarDefaults.inputFieldShape,
        windowInsets = SearchBarDefaults.windowInsets,
        placeholder = {
            Text(
                text = "Enter your query here",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        onQueryChange = { typing ->
            text = typing
            userAction.onNext(SearchUserAction.UserTyping(text))
        },
        onSearch = { query ->
            var isQueryAlreadySaved = false
            searchQueries.forEach { searchQuery ->
                if (searchQuery.query.equals(query, true)) {
                    isQueryAlreadySaved = true
                }
            }
            userAction.onNext(SearchUserAction.QuerySubmittedSuccess(query))
            if (!isQueryAlreadySaved) {
                queryAction.onNext(SearchQueryUserAction.SaveQuery(SearchQuery(query = query)))
            }
            keyboardController?.hide()
        },
        active = isActive,
        onActiveChange = { active ->
            when (active) {
                true -> {
                    Log.i("activity_search_awaiting", "a")
                    userAction.onNext(SearchUserAction.AwaitingInput)
                }
                false -> Log.i("activity_search_inactive", "search bar is inactive")
            }
            isActive = active
        },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                "Search Icon"
            )
        },
        trailingIcon = {
            when (text.isNotBlank()) {
                true -> {
                    Icon(
                        Icons.Default.Close,
                        "clear search",
                        modifier = Modifier
                            .clickable {
                                text = ""
                            }
                    )
                }
                false -> {
                    Log.i("trailing_icon_search", "text empty do not show trailing icon")
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(
                when(isActive) {
                    true -> 0.25f
                    false -> 0.095f
                }
            )
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxHeight()
        ) {
            DisplayPreviousSearchQueries(searchQueries, queryAction)
        }
    }
}

@Composable
fun DisplayPreviousSearchQueries(searchQueries: List<SearchQuery>,
                                 queryAction: PublishSubject<SearchQueryUserAction>) {
    searchQueries.forEach { query ->
        DisplaySearchQueryListItem(query, queryAction)
    }
}

@Composable
fun DisplaySearchQueryListItem(searchQuery: SearchQuery,
                               queryAction: PublishSubject<SearchQueryUserAction>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 9.dp, vertical = 4.dp)
            .clickable {
                queryAction.onNext(SearchQueryUserAction.SelectQuery(searchQuery))
            }
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "previous search"
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth(0.03f)
        )
        Text(
            text = searchQuery.query
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth(0.85f)
        )
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "delete recent search",
                modifier = Modifier
                    .clickable {
                        queryAction.onNext(SearchQueryUserAction.DeleteQuery(searchQuery))
                    }
            )
        }
    }
}


@Composable
fun UpdateRecentTextWithQueryState(queryState: SearchQueryState,
                                   userAction: PublishSubject<SearchUserAction>): String {
    var recentText = ""
    when (queryState) {
        SearchQueryState.DeletingQuery -> {
            Log.i("activity_query_delete", "deleting query")
        }
        is SearchQueryState.QueryError -> {
            Log.e("activity_query_error", "error query")
        }
        SearchQueryState.SavingQuery -> {
            Log.i("activity_query_save", "saving query")
        }
        is SearchQueryState.SelectedQuery -> {
            Log.i("activity_query_selected", "selecting query state")
            recentText = queryState.searchQuery.query
            userAction.onNext(SearchUserAction.AwaitingInput)
        }
        SearchQueryState.SetupState -> {
            Log.i("activity_query_setup", "setup query state")
        }
        is SearchQueryState.GetAllQueries -> {
            Log.i("activity_query_all", "all query state")
        }
    }
    return recentText
}

@Composable
fun DisplaySearchResultsFromViewState(viewState: SearchViewState) {
    when (viewState) {
        SearchViewState.Loading -> {
            Log.i("activity_search_loading", "search results are loading")
        }
        is SearchViewState.ProductLoadError -> {
            Log.e("activity_search_error", "encountered error: ${viewState.throwable}")
        }
        is SearchViewState.ProductLoaded -> {
            Log.i("activity_search_loaded", "search results are loaded")
            DisplaySearchResults(searchResults = viewState.page.searchResults)
        }
        SearchViewState.SetupLoading -> {
            Log.i("activity_search_setup", "search results are setup for loading")
        }
    }
}

@Composable
fun DisplaySearchResults(searchResults: List<SearchResult>) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(
            items = searchResults,
            itemContent = { item ->
                DisplaySearchResultListItem(searchResult = item)
            }
        )
    }
}

@Composable
fun DisplaySearchResultListItem(searchResult: SearchResult) {
    Card(
        modifier = Modifier
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(3.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            searchResult.imageUrl?.let { searchResultImageUrl ->
                DisplaySearchResultImage(
                    imageUrl = searchResultImageUrl
                )
            }
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                searchResult.title?.let { searchResultTitle ->
                    DisplaySearchResultText(
                        text = searchResultTitle
                    )
                }

                searchResult.price?.let { searchResultPrice ->
                    DisplaySearchResultText(
                        text = searchResultPrice
                    )
                }
            }
        }

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DisplaySearchResultImage(imageUrl: String) {
    GlideImage(
        model = imageUrl,
        contentScale = ContentScale.Inside,
        modifier = Modifier
            .fillMaxWidth(0.2f),
        contentDescription = "image default"
    )
}

@Composable
fun DisplaySearchResultText(text: String) {
    Text(
        text = text
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchResults() {
    CustomAppTheme() {
        val imageurl = "https://cdn.shopify.com/s/files/1/2579/8156/products/045-FEK_360x.jpg"
        val imageurl2 = "https://cdn.shopify.com/s/files/1/2579/8156/products/001freshegokidXboxblacktrucker_360x.jpg"
        val detailsUrl = "https://www.freshegokid.com/products/new-era-flower-print-trucker-in-black?"
        val detailsUrl2 = "https://www.freshegokid.com/products/xbox-black-trucker"
        val mockList = remember {
            listOf(
                SearchResult("key", "title", "price", imageurl, detailsUrl),
                SearchResult("key2", "title2", "price2", imageurl2, detailsUrl2)
            )
        }
        val viewState = SearchViewState.ProductLoaded(
            page = ProductListPage()
        )
        viewState.page.searchResults = mockList
        DisplaySearchResultsFromViewState(viewState = viewState)
    }
}

@Composable
fun <T: Any> rememberMutableStateListOf(vararg elements: T): SnapshotStateList<T> {
    return rememberSaveable(
        saver = listSaver(
            save = { stateList ->
                if (stateList.isNotEmpty()) {
                    val first = stateList.first()
                    if (!canBeSaved(first)) {
                        throw IllegalStateException("${first::class} cannot be saved. By default only types which can be stored in the Bundle class can be saved.")
                    }
                }
                stateList.toList()
            },
            restore = { it.toMutableStateList() }
        )
    ) {
        elements.toList().toMutableStateList()
    }
}