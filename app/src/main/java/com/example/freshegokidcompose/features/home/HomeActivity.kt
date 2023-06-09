package com.example.freshegokidcompose.features.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rxjava2.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.freshegokidcompose.data.model.home.HomePage
import com.example.freshegokidcompose.ui.theme.CustomAppTheme
import com.example.freshegokidcompose.view.DisplayHomeBanner
import com.example.freshegokidcompose.view.DisplayHomeTitle
import com.example.freshegokidcompose.view.DisplaySearchResults
import com.example.freshegokidcompose.view.DisplayTopAppBar
import com.example.freshegokidcompose.features.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val page by remember {
                viewModel.getHomeBannerAndHomeSearchResults()
            }.subscribeAsState(initial = HomePage())
            CustomAppTheme(darkTheme = false) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DisplayTopAppBar()
                    DisplayHomeTitle()
                    page.bannerUrl?.let { homeBanner ->
                        DisplayHomeBanner(homeBanner)
                    }
                    DisplaySearchResults(searchResults = page.products)
                }
            }
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DisplayTopAppBar() {
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
//                onClick = { }
//            )
//        }
//    )
//}
//
//@Composable
//fun DisplayHomeTitle() {
//    Text(
//        modifier = Modifier
//            .padding(0.dp, 16.dp),
//        text = "Seasonal exclusives!",
//        style = MaterialTheme.typography.titleMedium
//    )
//}
//
//@OptIn(ExperimentalGlideComposeApi::class)
//@Composable
//fun DisplayHomeBanner(bannerUrl: String) {
//    GlideImage(
//        model = bannerUrl,
//        contentDescription = "banner default",
//        contentScale = ContentScale.FillWidth,
//        modifier = Modifier
//            .padding(4.dp, 0.dp)
//    )
//}
//
//@Composable
//fun DisplaySearchResults(searchResults: List<SearchResult>) {
//    LazyColumn(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .fillMaxWidth()
//    ) {
//        items(
//            items = searchResults,
//            itemContent = { item ->
//                DisplaySearchResultListItem(searchResult = item)
//            }
//        )
//    }
//}
//
//@Composable
//fun DisplaySearchResultListItem(searchResult: SearchResult) {
//    Card(
//        modifier = Modifier
//            .padding(10.dp)
////        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .padding(3.dp)
//                .fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Start
//        ) {
//            searchResult.imageUrl?.let { searchResultImageUrl ->
//                DisplaySearchResultImage(
//                    imageUrl = searchResultImageUrl
//                )
//            }
//            Column(
//                horizontalAlignment = Alignment.Start,
//                verticalArrangement = Arrangement.SpaceEvenly
//            ) {
//                searchResult.title?.let { searchResultTitle ->
//                    DisplaySearchResultText(
//                        text = searchResultTitle
//                    )
//                }
//
//                searchResult.price?.let { searchResultPrice ->
//                    DisplaySearchResultText(
//                        text = searchResultPrice
//                    )
//                }
//            }
//        }
//
//    }
//}
//
//@OptIn(ExperimentalGlideComposeApi::class)
//@Composable
//fun DisplaySearchResultImage(imageUrl: String) {
//    GlideImage(
//        model = imageUrl,
//        contentScale = ContentScale.Inside,
//        modifier = Modifier
//            .fillMaxWidth(0.2f),
//        contentDescription = "image default"
//    )
//}
//
//@Composable
//fun DisplaySearchResultText(text: String) {
//    Text(
//        text = text
//    )
//}
//
//@Composable
//fun Greeting(name: String) {
//    Text(text = "Hello $name!")
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    CustomAppTheme() {
//        val imageurl = ""
//        val mockList = listOf(
//            SearchResult("key", "title", "price", "imageurl", "detailsurl"),
//            SearchResult("key2", "title2", "price2", "imageurl2", "detailsurl2")
//        )
//        DisplaySearchResults(searchResults = mockList)
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewHomeBanner() {
//    CustomAppTheme() {
//        val sampleBanner = "https://cdn.shopify.com/s/files/1/2579/8156/files/fresh_ego_kid_summer_headwear_300x.jpg"
//        DisplayHomeBanner(bannerUrl = sampleBanner)
//    }
//}