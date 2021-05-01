package ahmed.adel.sleeem.clowyy.souq.pojo

import ahmed.adel.sleeem.clowyy.souq.R

data class ReviewItem(
    val profileImage: Int = R.drawable.profile1,
    val username: String = "James Lawson" ,
    val rating: Float = 5f,
    val review: String = "air max are always very comfortable fit, clean and just perfect in every way. just the box was too small and scrunched the sneakers up a little bit, not sure if the box was always this small but the 90s are and will always be one of my favorites.",
    val img1: Int = R.drawable.product2,
    val img2: Int= R.drawable.product2,
    val img3: Int= R.drawable.product2,
    val date: String = "December 10, 2016"
)