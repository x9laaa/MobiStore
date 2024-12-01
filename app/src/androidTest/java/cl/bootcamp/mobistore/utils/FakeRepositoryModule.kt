package cl.bootcamp.mobistore.utils

import cl.bootcamp.mobistore.di.RepositoryModule
import cl.bootcamp.mobistore.model.DetailProduct
import cl.bootcamp.mobistore.model.Product
import cl.bootcamp.mobistore.model.Products
import cl.bootcamp.mobistore.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
class FakeRepositoryModule {

    @Provides
    @Singleton
    fun productRepository(): ProductRepository = object : ProductRepository {

        private val products = MutableStateFlow<List<Product>>(emptyList())

        override suspend fun getProducts(): ArrayList<Products> {
            return arrayListOf(
                Products(1, "Product 1", 100, "http://..."),
                Products(2, "Product 2", 200, "http://..."),
                Products(3, "Product 3", 300, "http://..."),
                Products(4, "Product 4", 400, "http://..."),
                Products(5, "Product 5", 500, "http://..."),
            )
        }

        override fun getAllProductsRoom(): Flow<List<Product>> {
            TODO("Not yet implemented")
        }

        override suspend fun getProductById(id: Int): DetailProduct {
            TODO("Not yet implemented")
        }
    }
}