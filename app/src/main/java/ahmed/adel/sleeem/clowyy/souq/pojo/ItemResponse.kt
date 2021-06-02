package ahmed.adel.sleeem.clowyy.souq.pojo

class ItemResponse : ArrayList<ItemResponse.itemResponseItem>(){
    data class itemResponseItem(
        val brand: String,
        val category: Category,
        val color: List<String>,
        val companyName: String,
        val description: String,
        val id: Double,
        val image: String,
        val price: Double,
        val quantity: Double,
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