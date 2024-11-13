package cl.bootcamp.mobistore.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.bootcamp.mobistore.model.Product
import cl.bootcamp.mobistore.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    val products: Flow<List<Product>> by lazy {
        repository.getAllProductsRoom()
    }

    init {
        getAllApi()
    }


    private fun getAllApi() {
        viewModelScope.launch {
            repository.getProducts()
        }
    }
}
