package ahmed.adel.sleeem.clowyy.souq.pojo.response
class CategoryResponse : ArrayList<CategoryResponse.CategoryResponseItem>(){
    data class CategoryResponseItem(
        val name: String,
        val url: String
    )
}