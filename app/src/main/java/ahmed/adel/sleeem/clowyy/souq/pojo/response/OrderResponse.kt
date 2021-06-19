package ahmed.adel.sleeem.clowyy.souq.pojo.response

data class OrderResponse(
    val Address: String,
    val __v: Int,
    val _id: String,
    val importCharge: Int,
    val itemIds: List<ItemId>,
    val orderCode: String,
    val orderDate: String,
    val orderState: String,
    val totalPrice: Double,
    val userId: String
) {
    data class ItemId(
        val color: String,
        val count: Int,
        val companyName : String,
        val id: String,
        val size: String
    )
}