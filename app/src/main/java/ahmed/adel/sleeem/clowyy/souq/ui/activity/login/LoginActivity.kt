package ahmed.adel.sleeem.clowyy.souq.ui.activity.login

import ahmed.adel.sleeem.clowyy.souq.databinding.ActivityLoginBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.LoginRequest
import ahmed.adel.sleeem.clowyy.souq.pojo.LoginResponse
import ahmed.adel.sleeem.clowyy.souq.ui.activity.SplashActivity
import ahmed.adel.sleeem.clowyy.souq.utils.LoginUtils
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginResponse: LoginResponse
    private lateinit var viewModel: LoginViewModel
    override fun onStart() {
        super.onStart()
        // If user logged in, go directly to ProductActivity
        val isLogin = LoginUtils.getInstance(applicationContext)!!.getLogin()
        if (LoginUtils.getInstance(this)!!.isLoggedIn(isLogin)) {
            goToSplashActivity()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()

        binding.RegisterLoginTextView.setOnClickListener(this)
        binding.signInLoginButton.setOnClickListener(this)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
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
        val token = LoginUtils.getInstance(this)!!.userInfo().token
        // viewModel.loginUser(loginRequist, token!!)
        viewModel.loginUser(loginRequist)

        viewModel.login.observe(this, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Log.e("sssss", "Loading........")
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()

                }
                Resource.Status.SUCCESS -> {
                    it.data.let {
                        loginResponse = it!!
                        LoginUtils.getInstance(applicationContext)!!.saveUserInfo(loginResponse)
                        LoginUtils.getInstance(applicationContext)!!.saveUserInfo(loginRequist)
                        LoginUtils.getInstance(applicationContext)!!.setLogin(true)
                        goToSplashActivity()
                    }
                }
            }
        })
    }

    private fun goToSplashActivity() {
        startActivity(
            Intent(this@LoginActivity, SplashActivity::class.java)
        )
        finish()
    }

}