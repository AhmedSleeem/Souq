package ahmed.adel.sleeem.clowyy.souq.api

import ahmed.adel.sleeem.clowyy.souq.pojo.ProductResponse
import retrofit2.Response
import retrofit2.http.GET

interface ItemWebServices {
    @GET("products/getall")
    suspend fun getAllItems(): Response<ProductResponse>
}