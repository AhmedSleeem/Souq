package ahmed.adel.sleeem.clowyy.souq.pojo

import java.io.Serializable

class ProductResponse : ArrayList<ProductResponse.Item>(){


    data class Item(
        val brand: String,
        val category: Category,
        val color: List<String>,
        val companyName: String,
        val description: String,
        val id: Int,
        val image: String,
        val price: Double,
        val quantity: Int,
        val rating: Float,
        val sale: Sale,
        val size: List<String>,
        val title: String,

        //cart data
        var selectedColor : String ? = null,
        var selectedSize : String ? = null,
        var countOfSelectedItem : Int  = 1

    ) : Serializable{
        data class Category(
            val name: String,
            val url: String
        )
        data class Sale(
            val amount: Int,
            val duration: String,
            val image: List<String>?,
            val type: String
        )
    }
}