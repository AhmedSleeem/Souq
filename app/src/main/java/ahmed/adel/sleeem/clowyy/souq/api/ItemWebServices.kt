package ahmed.adel.sleeem.clowyy.souq.api

import ahmed.adel.sleeem.clowyy.souq.pojo.request.*
import ahmed.adel.sleeem.clowyy.souq.pojo.response.*
import retrofit2.Response
import retrofit2.http.*

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

    @GET("review/reviewbyitemid")
    suspend fun getReviewsByItemId(@Query("itemId") id:String): Response<ReviewResponse>

    @GET("review/reviewbyrating")
    suspend fun getReviewsByRate(@Query("itemId") id:String,
                                 @Query("rating") rating:Int): Response<ReviewResponse>

    @POST("review/addreview")
    suspend fun postReview(@Body reviewRequest: ReviewRequest): Response<ReviewResponse.Item>

    @GET("users/getuserbyid")
    suspend fun getUserById(@Query("id") id:String): Response<UserResponse>

    @PUT("users/modifyaccount")
    suspend fun updateAccount(@Body userRequist : UserRequist): Response<UserResponse>

    @PUT("users/changepassword")
    suspend fun updatePassword(@Body passwordRequest: PasswordRequest): Response<PasswordResponse>

    @POST("auth")
    suspend fun loginUser(
        @Body request: LoginRequest,
        // @Header("x-auth-token") token: String
    ): Response<LoginResponse>

    @POST("users")
    suspend fun registerUser(
        @Body request: RegisterRequest,
    ): Response<RegisterResponse>

}