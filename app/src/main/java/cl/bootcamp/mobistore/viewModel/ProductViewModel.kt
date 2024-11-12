package cl.bootcamp.mobistore.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.bootcamp.mobistore.model.Product
import cl.bootcamp.mobistore.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {
    private val _productState = MutableStateFlow<List<Product>>(emptyList())
    val productState: StateFlow<List<Product>> = _productState

    init {
        fetchProducts()
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            try {
                val products = repository.getProducts()
                _productState.value = products
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

  suspend fun getProductById(id: Int): Product {
        return repository.getProductById(id)
    }
}
