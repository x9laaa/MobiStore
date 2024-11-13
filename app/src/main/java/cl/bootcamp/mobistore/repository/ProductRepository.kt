package cl.bootcamp.mobistore.repository

import cl.bootcamp.mobistore.dataSource.RestDataSource
import cl.bootcamp.mobistore.model.Product
import cl.bootcamp.mobistore.model.ProductDao
import cl.bootcamp.mobistore.model.Products
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ProductRepository {
    suspend fun getProducts(): ArrayList<Products>
    fun getAllProductsRoom(): Flow<List<Product>>
}

class ProductRepositoryImpl @Inject constructor(
    private val restDataSource: RestDataSource,
    private val productDao: ProductDao
) : ProductRepository {

    override suspend fun getProducts(): ArrayList<Products> {
        val data = restDataSource.getProducts()

        data.forEach {
            val product = Product(it.id, it.name, it.price, it.image)
            productDao.insert(product)
        }
        return ArrayList(data)
    }

    override fun getAllProductsRoom(): Flow<List<Product>> = productDao.getAllProducts()
}