package ahmed.adel.sleeem.clowyy.souq.api

import ahmed.adel.sleeem.clowyy.souq.pojo.CategoryResponse
import ahmed.adel.sleeem.clowyy.souq.pojo.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ItemWebServices {
    @GET("products/getall")
    suspend fun getAllItems(): Response<ProductResponse>

    @GET("products/getsales")
    suspend fun getSaleItems(): Response<ProductResponse>

    @GET("products/getcategories")
    suspend fun getCategory(): Response<CategoryResponse>

    @GET("products/getbycategoryname")
    suspend fun getItemsByCategory(@Query("category") categoryTitle:String): Response<ProductResponse>

    @GET("products/getbytitle")
    suspend fun getItemsByTitle(@Query("title") title:String): Response<ProductResponse>
}