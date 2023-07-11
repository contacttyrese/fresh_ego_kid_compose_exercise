package com.example.freshegokidcompose.features.productdetails

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rxjava2.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.freshegokidcompose.features.navigationbar.view.DisplayTopAppBar
import com.example.freshegokidcompose.features.productdetails.viewmodel.DetailsViewModel
import com.example.freshegokidcompose.data.model.productdetails.ProductDetailsPage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsActivity : ComponentActivity() {
    private val viewModel: DetailsViewModel by viewModels()
    private var _detailsUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras?.getString("detailsUrl")?.let { detailsUrl ->
            Log.i("activity_detail", "attempting to get extras from intent")
            _detailsUrl = detailsUrl
            viewModel.setRequiredProductDetails(_detailsUrl)
        } ?: kotlin.run {
            RuntimeException("details url in extras should not be null")
        }
        viewModel.setRequiredProductDetails(_detailsUrl)
        setContent {
            val page by remember {
                viewModel.getProductDetails()
            }.subscribeAsState(initial = ProductDetailsPage())
            Column(
                modifier = Modifier
                    .verticalScroll(
                        state = rememberScrollState(),
                        enabled = true
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DisplayTopAppBar(
                    heading = "Product Details",
                    isHomeEnabled = true,
                    isSearchEnabled = true
                )
                page.details.images?.let { images ->
                    images[0].src?.let { url ->
                        DisplayDetailsImage(url)
                    }
                }
                page.details.title?.let { title ->
                    DisplayDetailsTitle(title)
                }
                page.details.variants?.let { variants ->
                    variants[0].price?.let { price ->
                        DisplayDetailsPrice(price)
                    }
                }
                page.details.description?.let { description ->
                    DisplayDetailsDescription(description)
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DisplayDetailsImage(imageUrl: String) {
    GlideImage(
        model = imageUrl,
        contentScale = ContentScale.Inside,
        contentDescription = "details image displayed",
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxSize(0.6f)
    )
}

@Composable
fun DisplayDetailsTitle(title: String) {
    Text(
        modifier = Modifier
            .padding(8.dp, 16.dp),
        text = title,
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
fun DisplayDetailsPrice(price: String) {
    Text(
        modifier = Modifier
            .padding(8.dp, 8.dp),
        text = "£$price",
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
fun DisplayDetailsDescription(description: String) {
    Text(
        modifier = Modifier
            .padding(16.dp, 16.dp),
        text = description,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewProductDetails() {
    val imageUrl = "https://cdn.shopify.com/s/files/1/2579/8156/files/017-FEK_0b5b074a-34d1-41dd-9884-bc9a80f95035.jpg"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DisplayDetailsImage(imageUrl)
        DisplayDetailsTitle(title = "Test Title")
        DisplayDetailsPrice(price = "9.99")
        DisplayDetailsDescription(description = "this is a test description")
    }
}

