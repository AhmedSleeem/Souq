package ahmed.adel.sleeem.clowyy.souq.pojo

class ProductResponse : ArrayList<ProductResponse.Item>(){
    data class Item(
        val category: String,
        val companyName: String,
        val description: String,
        val id: Int,
        val image: String,
        val price: Double,
        val quantity: Int,
        val rating: Float,
        val sale: Sale,
        val title: String
    ) {
        data class Sale(
            val amount: Int,
            val duration: Int,
            val type: String
        )
    }
}