package ahmed.adel.sleeem.clowyy.souq.api

import ahmed.adel.sleeem.clowyy.souq.pojo.*
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
    suspend fun getItemsByCategory(@Query("category") categoryTitle: String): Response<ProductResponse>

    @GET("products/getbytitle")
    suspend fun getItemsByTitle(@Query("title") title: String): Response<ProductResponse>


    @GET("products/filter")
    suspend fun filterProducts(
        @Query("min") min: Int? = null,
        @Query("max") max: Int? = null,
        @Query("category") category: String? = null,
        @Query("sale") sale: Int = 0,
        @Query("brand") brand: String? = null,
        @Query("title") title: String? = null,
        @Query("price") price: Int = 0

    ): Response<ProductResponse>

    //https://souqitigraduationproj.herokuapp.com/api/products/filter?min=100&max=1000&category=electronics&sale=1&brand=ACER&title=Fjallrave&price=1
    @PUT("users/modifyaccount")
    suspend fun updateAccount(@Body userRequist: UserRequist): Response<UserResponse>

    @POST("order/add")
    suspend fun addOrder(@Body orderRequest: OrderRequest): Response<OrderResponse>


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
    ): Response<OrderResponse>

    //https://souqitigraduationproj.herokuapp.com/api/products/getitembyid?id=20
    @GET("products/getitembyid")
    suspend fun getItemsById(@Query("id") id: String): Response<ItemResponse>
}