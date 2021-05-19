package ahmed.adel.sleeem.clowyy.souq.api

import ahmed.adel.sleeem.clowyy.souq.pojo.ItemResponse
import ahmed.adel.sleeem.clowyy.souq.pojo.LoginRequest
import ahmed.adel.sleeem.clowyy.souq.pojo.LoginResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ItemWebServices {
    @GET("products/getall")
    suspend fun getAllItems(): Response<ItemResponse>
}