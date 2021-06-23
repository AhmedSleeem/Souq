package ahmed.adel.sleeem.clowyy.souq.api

import ahmed.adel.sleeem.clowyy.souq.pojo.*
import ahmed.adel.sleeem.clowyy.souq.pojo.request.*
import ahmed.adel.sleeem.clowyy.souq.pojo.response.*
import ahmed.adel.sleeem.clowyy.souq.pojo.response.ItemResponse
import retrofit2.Response
import retrofit2.http.*

interface ItemWebServices {
    @GET("products/getall")
    suspend fun getAllItems(): Response<ProductResponse>

    @GET("products/getsales")
    suspend fun getSaleItems(@Query("page") page:Int=1): Response<ProductResponse>

    @GET("products/getcategories")
    suspend fun getCategory(): Response<CategoryResponse>

    @GET("products/getbycategoryname")
    suspend fun getItemsByCategory(@Query("category") categoryTitle:String): Response<ProductResponse>

    @GET("products/getbytitle")
    suspend fun getItemsByTitle(@Query("title") title:String): Response<ProductResponse>

    @GET("products/filter")
    suspend fun filterProducts(
        @Query("min") min: Int? = null,
        @Query("max") max: Int? = null,
        @Query("category") category: String? = null,
        @Query("sale") sale: Int = 0,
        @Query("brand") brand: String? = null,
        @Query("title") title: String? = null,
        @Query("price") price: Int = 0,
        @Query("page") page: Int = 1
    ): Response<FilterResponse>


    @POST("order/add")
    suspend fun addOrder(@Body orderRequest: OrderRequest): Response<OrderResponse>


    @GET("review/reviewbyitemid")
    suspend fun getReviewsByItemId(@Query("itemId") id:String): Response<ReviewResponse>

    @GET("review/reviewbyrating")
    suspend fun getReviewsByRate(@Query("itemId") id:String,
                                 @Query("rating") rating:Int): Response<ReviewResponse>

    @POST("review/addreview")
    suspend fun postReview(@Body reviewRequest: ReviewRequest): Response<ReviewResponse.Item>

    @HTTP(method = "DELETE", path = "review/deleteReview", hasBody = true)
    suspend fun deleteReview(@Body deleteReviewRequest: DeleteReviewRequest): Response<DeleteReviewResponse>

    @PUT("review/modifyreview")
    suspend fun modifyReview(@Body modifyReviewRequest: ModifyReviewRequest): Response<ReviewResponse.Item>

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

    @GET("order/orderbyuserid")
    suspend fun getOrders(
        @Query("id") id: String
    ): Response<OrdersByIdResponse>

    @GET("products/getitembyid")
    suspend fun getItemsById(@Query("id") id: String): Response<ItemResponse>

    @GET("products/getitembyid")
    suspend fun getItemsByIdFav(@Query("id") id: String): Response<ProductResponse.Item>

    //https://souqitigraduationproj.herokuapp.com/api/order/delete
    @HTTP(method = "DELETE", path = "order/delete", hasBody = true)
    suspend fun deleteOrderById(@Body deleteOrderRequest: DeleteOrderRequest): Response<DeleteOrderResponse>
}