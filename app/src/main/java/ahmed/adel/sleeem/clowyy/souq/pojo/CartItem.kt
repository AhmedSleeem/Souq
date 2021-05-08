package ahmed.adel.sleeem.clowyy.souq.pojo

import ahmed.adel.sleeem.clowyy.souq.R


data class CartItem(
    val itemImage : Int = R.drawable.shoes,
    val itemDesc: String = "Nike Air Zoom Pegasus 36 Miami",
    val itemPrice: String = "\$299,43" ,
    val itemCount: Int = 1,
    val itemIsFavorite: Boolean = true,
)