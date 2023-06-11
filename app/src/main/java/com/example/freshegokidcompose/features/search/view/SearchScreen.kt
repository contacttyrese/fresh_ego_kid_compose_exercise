package com.example.freshegokidcompose.features.search.view

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import io.reactivex.subjects.PublishSubject

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun DisplaySearchBar(userAction: PublishSubject<SearchUserAction>) {
    var text by rememberSaveable {
        mutableStateOf("")
    }
    var isActive by rememberSaveable {
        mutableStateOf(false)
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    SearchBar(
        query = text,
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
            userAction.onNext(SearchUserAction.QuerySubmittedSuccess(query))
            keyboardController?.hide()
        },
        active = true,
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
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.125f)
    ) {

    }
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
//        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
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