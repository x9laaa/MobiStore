package cl.bootcamp.mobistore.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.bootcamp.mobistore.model.Product
import cl.bootcamp.mobistore.repository.ProductRepository
import cl.bootcamp.mobistore.state.ProductStatate
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

    var state by mutableStateOf(ProductStatate())
        private set

    init {
        getAllApi()
    }


    private fun getAllApi() {
        viewModelScope.launch {
            repository.getProducts()
        }
    }

    fun getProductById(id: Int) {
        viewModelScope.launch {
            val product = repository.getProductById(id)

            state = state.copy(
                id = product.id,
                name = product.name,
                price = product.price,
                image = product.image,
                description = product.description,
                lastPrice = product.lastPrice,
                credit = product.credit
            )
        }
    }
    fun cleanState() {
        state = state.copy(
            id = 0,
            name = "",
            price = 0,
            image = "",
            description = "",
            lastPrice = 0,
            credit = false
        )
    }
}
