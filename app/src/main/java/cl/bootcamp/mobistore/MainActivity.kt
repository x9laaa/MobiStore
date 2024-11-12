package cl.bootcamp.mobistore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import cl.bootcamp.mobistore.ui.theme.MobiStoreTheme
import cl.bootcamp.mobistore.view.ProductListView
import cl.bootcamp.mobistore.viewModel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        val viewModel: ProductViewModel by viewModels()
        setContent {
            MobiStoreTheme {
                ProductListView(viewModel)
            }
        }
    }
}
