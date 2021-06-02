package ahmed.adel.sleeem.clowyy.souq.pojo.request

data class OrderRequest(
    var Address: String? = "",
    val importCharge: Int? = 13,
    var itemIds: List<ItemId>?,
    var orderCode: String? = "",
    var orderDate: String? = "",
    val orderState:String? = "shipping",
    var userId: String? = ""
) {
    data class ItemId(
        var color: String? = "",
        var count: Int? = 1,
        var id: String? = "",
        var size: String? = ""
    )
}