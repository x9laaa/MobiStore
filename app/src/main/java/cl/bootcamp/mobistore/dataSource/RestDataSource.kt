package cl.bootcamp.mobistore.dataSource

import cl.bootcamp.mobistore.model.Product
import cl.bootcamp.mobistore.util.Constants.Companion.ENDPOINT_PRODUCTS
import retrofit2.http.GET
import retrofit2.http.Path

interface RestDataSource {
    @GET(ENDPOINT_PRODUCTS)
    suspend fun getProducts(): List<Product>

    @GET("$ENDPOINT_PRODUCTS/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product

}