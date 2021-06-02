package ahmed.adel.sleeem.clowyy.souq.pojo.response

class ItemResponse : ArrayList<ItemResponse.itemResponseItem>(){
    data class itemResponseItem(
        val brand: String,
        val category: Category,
        val color: List<String>,
        val companyName: String,
        val description: String,
        val id: Int,
        val image: String,
        val price: Double,
        val quantity: Int,
        val rating: Double,
        val size: List<String>,
        val title: String
    ) {
        data class Category(
            val name: String,
            val url: String
        )
    }
}