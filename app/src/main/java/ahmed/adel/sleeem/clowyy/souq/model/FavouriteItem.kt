package ahmed.adel.sleeem.clowyy.souq.model

import android.widget.RatingBar
import android.widget.TextView

class FavouriteItem {

    var productImage: Int? = 0
    var productName: String? = null
    var productCost: String? = null
    var productOldCost: String? = null
    var offerPercentage: String? = null


    constructor(
        productImage: Int?,
        productName: String?,
        productCost: String?,
        productOldCost: String?,
        offerPercentage: String?
    ) {
        this.productImage = productImage
        this.productName = productName
        this.productCost = productCost
        this.productOldCost = productOldCost
        this.offerPercentage = offerPercentage
    }
}