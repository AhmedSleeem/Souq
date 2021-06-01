package ahmed.adel.sleeem.clowyy.souq.pojo.request

data class ReviewRequest(
    val description: String,
    val itemId: String,
    val rating: Double,
    val userId: String
)