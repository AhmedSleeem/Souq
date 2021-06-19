package ahmed.adel.sleeem.clowyy.souq.pojo.request

import java.io.Serializable

data class OrderRequest(
    var Address: String="",
    var importCharge: Int=13,
    var itemIds: List<ItemId>?=null,
    var orderCode: String="",
    var orderDate: String="",
    var orderState: String="Packing",
    var totalPrice: Double=0.0,
    var userId: String=""
) :Serializable {
    data class ItemId(
        var color: String="",
        var count: Int=1,
        var id: String="",
        var companyName : String = "",
        var size: String=""
    )
}