package ahmed.adel.sleeem.clowyy.souq.pojo.response

import java.io.Serializable

class ReviewResponse : ArrayList<ReviewResponse.Item>(){
    data class Item(
        val __v: Int,
        val _id: String,
        val description: String,
        val itemId: String,
        val rating: Double,
        val userId: String,
        var user: UserResponse?=null
    ):Serializable
}