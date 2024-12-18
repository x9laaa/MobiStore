package cl.bootcamp.mobistore.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import cl.bootcamp.mobistore.components.AppBarView
import cl.bootcamp.mobistore.model.Product
import cl.bootcamp.mobistore.viewModel.ProductViewModel
import coil.compose.AsyncImage


@Composable
fun ProductListView(viewModel: ProductViewModel = hiltViewModel(), navController: NavHostController) {
    val products by viewModel.products.collectAsState(initial = emptyList())

    Scaffold(
        topBar = { AppBarView("MobiStore") }
    )
    { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(products) { product ->
                ProductCard(product, navController)
            }
        }
    }

}

@Composable
fun ProductCard(product: Product, navController: NavHostController) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate("productDetailView/${product.id}")
            }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                model = product.image,
                contentDescription = product.name,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = product.name, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Precio: $${product.price}", textAlign = TextAlign.Start)
        }
    }
}