package ahmed.adel.sleeem.clowyy.souq.pojo
class CategoryResponse : ArrayList<CategoryResponse.CategoryResponseItem>(){
    data class CategoryResponseItem(
        val name: String,
        val url: String
    )
}