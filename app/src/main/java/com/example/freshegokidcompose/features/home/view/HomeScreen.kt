package com.example.freshegokidcompose.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.freshegokidcompose.data.model.search.SearchResult
import com.example.freshegokidcompose.ui.theme.CustomAppTheme

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DisplayTopAppBar(context: Context) {
//    CenterAlignedTopAppBar(
//        title = {
//            Text(
//                style = MaterialTheme.typography.titleMedium,
//                text = "Fresh Ego Kid"
//            )
//        },
//        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//            containerColor = Color.Black,
//            titleContentColor = Color.White,
//            actionIconContentColor = Color.White,
//            navigationIconContentColor = Color.White
//        ),
//        navigationIcon = {
//            IconButton(
//                content = {
//                    Icon(
//                        Icons.Default.Home,
//                        "home"
//                    )
//                },
//                onClick = {}
//            )
//        },
//        scrollBehavior = null,
//        actions = {
//            IconButton(
//                content = {
//                    Icon(
//                        Icons.Default.Search,
//                        "search"
//                    )
//                },
//                onClick = {
//                    val intent = Intent(context, SearchActivity::class.java)
//                    ContextWrapper(context).startActivity(intent)
//                }
//            )
//        }
//    )
//}

@Composable
fun DisplayHomeTitle() {
    Text(
        modifier = Modifier
            .padding(0.dp, 16.dp),
        text = "Seasonal exclusives!",
        style = MaterialTheme.typography.titleMedium
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DisplayHomeBanner(bannerUrl: String) {
    GlideImage(
        model = bannerUrl,
        contentDescription = "banner default",
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .padding(4.dp, 0.dp)
    )
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

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CustomAppTheme() {
        val imageurl = "https://cdn.shopify.com/s/files/1/2579/8156/products/045-FEK_360x.jpg"
        val imageurl2 = "https://cdn.shopify.com/s/files/1/2579/8156/products/001freshegokidXboxblacktrucker_360x.jpg"
        val detailsUrl = "https://www.freshegokid.com/products/new-era-flower-print-trucker-in-black?"
        val detailsUrl2 = "https://www.freshegokid.com/products/xbox-black-trucker"
        val mockList = listOf(
            SearchResult("key", "title", "price", imageurl, detailsUrl),
            SearchResult("key2", "title2", "price2", imageurl2, detailsUrl2)
        )
        DisplaySearchResults(searchResults = mockList)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeBanner() {
    CustomAppTheme() {
        val sampleBanner = "https://cdn.shopify.com/s/files/1/2579/8156/files/fresh_ego_kid_summer_headwear_300x.jpg"
        DisplayHomeBanner(bannerUrl = sampleBanner)
    }
}