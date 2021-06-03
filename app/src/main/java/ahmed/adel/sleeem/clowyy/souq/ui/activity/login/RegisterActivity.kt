package ahmed.adel.sleeem.clowyy.souq.ui.activity.login

import ahmed.adel.sleeem.clowyy.souq.databinding.ActivityRegisterBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.request.RegisterRequest
import ahmed.adel.sleeem.clowyy.souq.utils.Constants
import ahmed.adel.sleeem.clowyy.souq.utils.LoginUtils
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(intent.getStringExtra("google") == "google"){
            val name = LoginUtils.getInstance(this)!!.userInfo().name
            val email = LoginUtils.getInstance(this)!!.userInfo().email
            binding.fullNameRegisterEditText.setText(name)
            binding.fullNameRegisterEditText.isEnabled =false
            binding.emailRegisterEditText.setText(email)
            binding.emailRegisterEditText.isEnabled =false
        }
        initViewModel()
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
                getToken()
            }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
    }

    private fun registerUser() {

        val name = binding.fullNameRegisterEditText.text.toString().trim()
        val email = binding.emailRegisterEditText.text.toString().trim()
        val password = binding.passwordRegisterEditText.text.toString().trim()
        val registerRequest = RegisterRequest(name, email, password)
        viewModel.registerUser(registerRequest)

        viewModel.register.observe(this, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Log.e("sssss", "Loading........")
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    Log.e("errorrrr",  it.message.toString())
                }
                Resource.Status.SUCCESS -> {
                    it.data.let { response ->
                        Log.e("sssss", response.toString())
                        LoginUtils.getInstance(this)!!.saveUserInfo(response!!)
                        updatePassword(password)
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()
                    }
                }
            }
        })
    }
    fun updatePassword(password: String) {
        val sharedPreferences = this.getSharedPreferences(Constants.USER_SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("password", password)
        editor.apply()
    }
    private fun getToken() {
        viewModel.token.observe(this, Observer { token ->
            saveToken(token)
        })
    }
    fun saveToken(token: String) {
        val sharedPreferences = this.getSharedPreferences(Constants.USER_SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("token", token)
        editor.apply()
    }
    fun checkValidity() {
        if (binding.fullNameRegisterEditText.text.toString().length < 6) {
            binding.fullNameRegisterEditText.error
        }
    }
}