package ahmed.adel.sleeem.clowyy.souq.pojo.request

data class OrderRequest(
    val Address: String,
    val importCharge: Int = 13,
    val itemIds: List<ItemId>,
    val orderCode: String,
    val orderDate: String,
    val orderState:String = "shipping",
    val userId: String
) {
    data class ItemId(
        val color: String,
        val count: Int,
        val id: String,
        val size: String
    )
}