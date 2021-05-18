package ahmed.adel.sleeem.clowyy.souq.pojo

class ItemResponse : ArrayList<ItemResponse.ItemResponseItem>(){
    data class ItemResponseItem(
        val __v: Int,
        val category: String,
        val description: String,
        val id: Int,
        val image: String,
        val price: Double,
        val rating: Int,
        val title: String
    )
}