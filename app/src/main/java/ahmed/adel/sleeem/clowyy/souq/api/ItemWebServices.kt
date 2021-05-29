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

    @GET("products/filter")
    suspend fun filterProducts(@Query("min") min:Int?=null,
                               @Query("max") max:Int?=null,
                               @Query("category") category:String?=null,
                               @Query("sale") sale:Int = 0,
                               @Query("brand") brand:String?=null,
                               @Query("title") title:String?=null,
                               @Query("price") price:Int=0

    ): Response<ProductResponse>
//https://souqitigraduationproj.herokuapp.com/api/products/filter?min=100&max=1000&category=electronics&sale=1&brand=ACER&title=Fjallrave&price=1
}