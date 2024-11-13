package cl.bootcamp.mobistore.dataSource

import cl.bootcamp.mobistore.model.DetailProduct
import cl.bootcamp.mobistore.model.Products
import cl.bootcamp.mobistore.util.Constants.Companion.ENDPOINT_DETAIL
import cl.bootcamp.mobistore.util.Constants.Companion.ENDPOINT_PRODUCTS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RestDataSource {
    @GET(ENDPOINT_PRODUCTS)
    suspend fun getProducts(): List<Products>

    @GET("$ENDPOINT_DETAIL/{id}")
    suspend fun getProductById(@Path("id") id: Int): DetailProduct
}