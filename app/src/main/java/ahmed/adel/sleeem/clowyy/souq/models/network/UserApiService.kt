package ahmed.adel.sleeem.clowyy.souq.models.network

import ahmed.adel.sleeem.clowyy.souq.pojo.LoginRequest
import ahmed.adel.sleeem.clowyy.souq.pojo.LoginResponse
import ahmed.adel.sleeem.clowyy.souq.pojo.RegisterRequest
import ahmed.adel.sleeem.clowyy.souq.pojo.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface UserApiService {

    @POST("users")
    fun registerUser(
        @Body request: RegisterRequest,
    ): Call<RegisterResponse>

    @POST("auth")
    fun loginUser(
        @Body request: LoginRequest,
        @Header("x-auth-token") token: String
    ): Call<LoginResponse>
}