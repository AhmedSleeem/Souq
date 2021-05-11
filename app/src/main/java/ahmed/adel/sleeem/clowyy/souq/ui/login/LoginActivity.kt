package ahmed.adel.sleeem.clowyy.souq.ui.login

import ahmed.adel.sleeem.clowyy.souq.databinding.ActivityLoginBinding
import ahmed.adel.sleeem.clowyy.souq.models.ApiManager
import ahmed.adel.sleeem.clowyy.souq.pojo.LoginRequest
import ahmed.adel.sleeem.clowyy.souq.pojo.LoginResponse
import ahmed.adel.sleeem.clowyy.souq.ui.MainActivity
import ahmed.adel.sleeem.clowyy.souq.utils.Constants.SHARED_TOKEN_NAME
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginResponse :LoginResponse
   private lateinit var token: String
    lateinit var preferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

         preferences= getSharedPreferences(SHARED_TOKEN_NAME, Context.MODE_PRIVATE)
        val retrivedToken = preferences.getString("TOKEN", null)
        if(!retrivedToken.isNullOrEmpty()){
            token = retrivedToken
            Log.e("TAG", "onCreate: ===> "+token )
        }
        binding.RegisterLoginTextView.setOnClickListener(this)
        binding.signInLoginButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.RegisterLoginTextView -> {
                startActivity(Intent(this, RegisterActivity::class.java))
                finish()
            }
            binding.signInLoginButton -> {
                loginUser()
            }
        }
    }

    fun loginUser() {
        val email = binding.emailLoginEditText.text.toString().trim()
        val password = binding.passwordLoginEditText.text.toString().trim()
        val loginRequist = LoginRequest(email, password)
        val token = token
        val registerResponseCall = ApiManager.apiService.loginUser(loginRequist, token)
        registerResponseCall.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    loginResponse = response.body()!!

                    preferences.edit().putString("TOKEN", response.body()!!.token).apply()
                    Log.e("TAG", "onCreate: ===> "+response.body()!!.token)
                    startActivity(
                        Intent(this@LoginActivity, MainActivity::class.java)
                    )
                    finish()
                } else {
                    Log.e(
                        "TAG",
                        "onResponse: errorBody ===>" + response.errorBody() + "   body ==>" + response.body()
                    )
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("TAG", "onFailure: " + t.localizedMessage)
            }

        })
    }
}