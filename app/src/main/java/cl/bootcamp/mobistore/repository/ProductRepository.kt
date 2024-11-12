package cl.bootcamp.mobistore.repository

import cl.bootcamp.mobistore.dataSource.RestDataSource
import cl.bootcamp.mobistore.model.Product
import javax.inject.Inject

class ProductRepository @Inject constructor(private val restDataSource: RestDataSource) {
    suspend fun getProducts(): List<Product> {
        return restDataSource.getProducts()
    }

    suspend fun getProductById(id: Int): Product {
        return restDataSource.getProductById(id)
    }

}