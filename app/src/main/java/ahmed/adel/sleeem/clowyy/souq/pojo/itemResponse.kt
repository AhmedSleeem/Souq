package ahmed.adel.sleeem.clowyy.souq.pojo

class itemResponse : ArrayList<itemResponse.itemResponseItem>(){
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
        val rating: Int,
        val size: List<String>,
        val title: String
    ) {
        data class Category(
            val name: String,
            val url: String
        )
    }
}