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
import androidx.compose.ui.platform.LocalContext
import com.example.freshegokidcompose.data.model.home.HomePage
import com.example.freshegokidcompose.ui.theme.CustomAppTheme
import com.example.freshegokidcompose.features.home.view.DisplayHomeBanner
import com.example.freshegokidcompose.features.home.view.DisplayHomeTitle
import com.example.freshegokidcompose.features.home.viewmodel.HomeViewModel
import com.example.freshegokidcompose.features.navigationbar.view.DisplayTopAppBar
import com.example.freshegokidcompose.features.search.view.DisplaySearchResults
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
                    DisplayTopAppBar(
                        LocalContext.current,
                        "Fresh Ego Kid",
                        false,
                        true
                    )
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