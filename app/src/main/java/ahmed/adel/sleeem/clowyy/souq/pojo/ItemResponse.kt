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
    ){
        override fun equals(other: Any?): Boolean {

            if (javaClass != other?.javaClass) return false
            other as ItemResponseItem

            if (id != other.id) return false
            if (category != other.category) return false
            if (image != other.image) return false
            if (price != other.price) return false
            if (rating != other.rating) return false
            if (title != other.title) return false

            return true
        }
    }
}