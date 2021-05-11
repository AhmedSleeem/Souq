package ahmed.adel.sleeem.clowyy.souq.ui.login

import ahmed.adel.sleeem.clowyy.souq.databinding.ActivityRegisterBinding
import ahmed.adel.sleeem.clowyy.souq.models.ApiManager
import ahmed.adel.sleeem.clowyy.souq.pojo.RegisterRequest
import ahmed.adel.sleeem.clowyy.souq.pojo.RegisterResponse
import ahmed.adel.sleeem.clowyy.souq.utils.Constants.SHARED_TOKEN_NAME
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.SignInRegisterTextView.setOnClickListener(this)
        binding.signUpRegisterButton.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v) {
            binding.SignInRegisterTextView -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            binding.signUpRegisterButton -> {
                registerUser()
            }
        }
    }

    fun registerUser() {
        val name = binding.fullNameRegisterEditText.text.toString().trim()
        val email = binding.emailRegisterEditText.text.toString().trim()
        val password = binding.passwordRegisterEditText.text.toString().trim()
        val registerRequist = RegisterRequest(name, email, password)
        val registerResponseCall = ApiManager.apiService.registerUser(registerRequist)
        registerResponseCall.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    val sharedPreferences = getSharedPreferences(SHARED_TOKEN_NAME, Context.MODE_PRIVATE)
                    val token = response.headers()["X-Auth-Token"]
                    sharedPreferences.edit().putString("TOKEN",token).apply()
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()
                } else {
                    Log.e("TAG", "onResponse: errorBody ===>" + response.errorBody() + "   body ==>" + response.body())
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e("TAG", "onFailure: " + t.localizedMessage)
            }

        })
    }
    fun checkValidity(){
        if (binding.fullNameRegisterEditText.text.toString().length < 6){
            binding.fullNameRegisterEditText.error
        }
    }
}