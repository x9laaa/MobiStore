package cl.bootcamp.mobistore.state

data class ProductStatate (
    val id: Int = 0,
    val name: String = "",
    val price: Int = 0,
    val image: String = "",
    val description: String = "",
    val lastPrice: Int = 0,
    val credit: Boolean = false
)