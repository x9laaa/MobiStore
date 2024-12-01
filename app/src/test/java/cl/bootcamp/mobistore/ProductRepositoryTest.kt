package cl.bootcamp.mobistore

import android.arch.core.executor.testing.InstantTaskExecutorRule
import cl.bootcamp.mobistore.dataSource.RestDataSource
import cl.bootcamp.mobistore.model.Product
import cl.bootcamp.mobistore.model.ProductDao
import cl.bootcamp.mobistore.repository.ProductRepositoryImpl
import cl.bootcamp.mobistore.util.Constants.Companion.ENDPOINT_DETAIL
import cl.bootcamp.mobistore.util.Constants.Companion.ENDPOINT_PRODUCTS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val product1 = Product(1, "Product 1", 100,  "http//...")
private val product2 = Product(2, "Product 2", 200, "http//...")

class ProductRepositoryTest {

    private val mockwebServer: MockWebServer = MockWebServer().apply {
        url("/")
        dispatcher = myDispatcher
    }

    private val restDataSource: RestDataSource = Retrofit.Builder()
        .baseUrl(mockwebServer.url("/"))
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RestDataSource::class.java)

    private val newsRepository = ProductRepositoryImpl(restDataSource, MockProductDao())

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @After
    fun tearDown() {
        mockwebServer.shutdown()
    }

    @Test
    fun `verificar si obtenemos correctamente los productos de la DB`() = runBlocking {
        val products = newsRepository.getAllProductsRoom().first()
        assertEquals(2, products.size)
    }

    @Test
    fun `verificar si obtenemos correctamente los productos de la API`() = runBlocking {
        val products = newsRepository.getProducts()
        assertEquals(10, products.size)
    }

    @Test
    fun `verificar si obtenemos correctamente el detalle de un producto`() = runBlocking {
        val product = newsRepository.getProductById(2)
        assertEquals("Samsung Galaxy A21s 64GB", product.name)
    }

}

val myDispatcher: Dispatcher = object : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        println("Request Path: ${request.path}")
        return when (request.path) {
            "/$ENDPOINT_PRODUCTS" -> MockResponse().apply { addResponse("api_product.json") }
            "/$ENDPOINT_DETAIL/2" -> MockResponse().apply { addResponse("api_product_detail.json") }
            else -> MockResponse().setResponseCode(404)
        }
    }
}

class MockProductDao : ProductDao {
    private val products = MutableStateFlow<List<Product>>(listOf(product1, product2))
    override suspend fun insert(product: Product) {
        products.value = products.value.toMutableList().apply {add(product)}
    }

    override fun getAllProducts(): Flow<List<Product>> = products
}


fun MockResponse.addResponse(filePath: String): MockResponse {
    val inputStream = javaClass.classLoader?.getResourceAsStream(filePath)
    val source = inputStream?.source()?.buffer()
    source?.let {
        setResponseCode(200)
        setBody(it.readString(Charsets.UTF_8))
    }
    return this
}