package ahmed.adel.sleeem.clowyy.souq.pojo

import java.io.Serializable
data class OrderResponse(
    val order: List<OrderResponseItem>,
    val totalPrice: Double
) {
    data class OrderResponseItem(
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
    ) : Serializable{
        data class ItemId(
            val color: String,
            val count: Int,
            val id: String,
            val size: String
        )
    }
}
//class OrderResponse : ArrayList<OrderResponse.OrderResponseItem>(){
//    data class OrderResponseItem(
//        val Address: String,
//        val __v: Int,
//        val _id: String,
//        val importCharge: Int,
//        val itemIds: List<ItemId>,
//        val orderCode: String,
//        val orderDate: String,
//        val orderState: String,
//        val totalPrice: Double,
//        val userId: String
//    ) : Serializable{
//        data class ItemId(
//            val color: String,
//            val count: Int,
//            val id: String,
//            val size: String
//        )
//    }
//}
