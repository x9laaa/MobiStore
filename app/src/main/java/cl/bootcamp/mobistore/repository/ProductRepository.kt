package cl.bootcamp.mobistore.repository

import cl.bootcamp.mobistore.dataSource.RestDataSource
import cl.bootcamp.mobistore.model.DetailProduct
import cl.bootcamp.mobistore.model.Product
import cl.bootcamp.mobistore.model.ProductDao
import cl.bootcamp.mobistore.model.Products
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ProductRepository {
    suspend fun getProducts(): ArrayList<Products>
    fun getAllProductsRoom(): Flow<List<Product>>
    suspend fun getProductById(id: Int): DetailProduct
}

class ProductRepositoryImpl @Inject constructor(
    private val restDataSource: RestDataSource,
    private val productDao: ProductDao
) : ProductRepository {

    override suspend fun getProductById(id: Int): DetailProduct {
        val data = restDataSource.getProductById(id)
        val product = DetailProduct(
            id = data.id,
            name = data.name,
            price = data.price,
            image = data.image,
            description = data.description ?: "",  // Aquí se maneja el caso null
            lastPrice = data.lastPrice,
            credit = data.credit
        )
        return product
    }

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