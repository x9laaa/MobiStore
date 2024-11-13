package cl.bootcamp.mobistore.view

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cl.bootcamp.mobistore.components.AppBarView
import cl.bootcamp.mobistore.viewModel.ProductViewModel
import coil.compose.AsyncImage
import coil.request.Disposable

@Composable
fun ProductDetailView(viewModel: ProductViewModel, navController: NavHostController, id: Int) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getProductById(id)
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.cleanState()
        }
    }

    Scaffold(
        topBar = {
            AppBarView(
                title = "Detalles del Producto",
                onBackNavClicked = { navController.popBackStack() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:info@novaera.cl")
                    putExtra(Intent.EXTRA_SUBJECT, "Consulta ${viewModel.state.name} - Id: ${viewModel.state.id}")
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Hola, me gustaría obtener más información del móvil ${viewModel.state.name} de código ${viewModel.state.id}. Quedo atento."
                    )
                }
                context.startActivity(Intent.createChooser(emailIntent, "Enviar correo..."))
            }) {
                Text("Email")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            AsyncImage(
                model = viewModel.state.image,
                contentDescription = viewModel.state.name,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = viewModel.state.name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Precio: $${viewModel.state.price}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Último Precio: $${viewModel.state.lastPrice}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Descripción: ${viewModel.state.description}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (viewModel.state.credit) "Acepta Crédito" else "Sólo Efectivo",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}