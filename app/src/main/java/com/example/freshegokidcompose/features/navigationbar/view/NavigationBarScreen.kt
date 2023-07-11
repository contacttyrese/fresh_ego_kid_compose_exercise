package com.example.freshegokidcompose.features.navigationbar.view

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.freshegokidcompose.features.home.HomeActivity
import com.example.freshegokidcompose.features.search.SearchActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayTopAppBar(heading: String,
                     isHomeEnabled: Boolean, isSearchEnabled: Boolean) {
    val context = LocalContext.current
    CenterAlignedTopAppBar(
        title = {
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = heading
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Black,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White,
            navigationIconContentColor = Color.White
        ),
        navigationIcon = {
            IconButton(
                content = {
                    Icon(
                        Icons.Default.Home,
                        "home"
                    )
                },
                onClick = {
                    val intent = Intent(context, HomeActivity::class.java)
                    ContextWrapper(context).startActivity(intent)
                },
                enabled = isHomeEnabled
            )
        },
        scrollBehavior = null,
        actions = {
            IconButton(
                content = {
                    Icon(
                        Icons.Default.Search,
                        "search"
                    )
                },
                onClick = {
                    val intent = Intent(context, SearchActivity::class.java)
                    ContextWrapper(context).startActivity(intent)
                },
                enabled = isSearchEnabled
            )
        }
    )
}