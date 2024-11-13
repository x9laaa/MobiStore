package cl.bootcamp.mobistore.model

data class ApiResponse (
    val result: ArrayList<Products>
)

data class Products(
    val id: Int,
    val name: String,
    val price: Int,
    val image: String
    )

data class DetailProduct(
    val id: Int,
    val name: String,
    val price: Int,
    val image: String,
    val description: String,
    val lastPrice: Int,
    val credit: Boolean

)
