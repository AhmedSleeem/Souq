package ahmed.adel.sleeem.clowyy.souq.ui.activity.login

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.ActivityLoginBinding
import ahmed.adel.sleeem.clowyy.souq.pojo.request.LoginRequest
import ahmed.adel.sleeem.clowyy.souq.pojo.response.LoginResponse
import ahmed.adel.sleeem.clowyy.souq.pojo.response.UserResponse
import ahmed.adel.sleeem.clowyy.souq.ui.activity.SplashActivity
import ahmed.adel.sleeem.clowyy.souq.utils.Constants
import ahmed.adel.sleeem.clowyy.souq.utils.LoginUtils
import ahmed.adel.sleeem.clowyy.souq.utils.Resource
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider


class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 121
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginResponse: LoginResponse
    private lateinit var viewModel: LoginViewModel
    override fun onStart() {
        super.onStart()
        // If user logged in, go directly to ProductActivity
        if (isLoggedIn(getLogin())) {
            goToSplashActivity()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setGooglePlusButtonText(binding.googleSignIn,getString(R.string.googleLogin)+"       ")
        val email = LoginUtils.getInstance(this)?.userInfo()?.email ?: ""
        val password = LoginUtils.getInstance(this)?.userInfo()?.password ?: ""
        binding.emailLoginEditText.setText(email)
        binding.passwordLoginEditText.setText(password)

        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        initViewModel()

        binding.RegisterLoginTextView.setOnClickListener(this)
        binding.signInLoginButton.setOnClickListener(this)
        binding.googleSignIn.setOnClickListener(this)
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
            binding.googleSignIn -> {
                loginUserWithGoogle()
            }
        }
    }
    private fun setGooglePlusButtonText(signInButton: SignInButton, buttonText: String) {
        for (i in 0 until signInButton.childCount) {
            val v = signInButton.getChildAt(i)
            if (v is TextView) {
                val tv = v
                tv.gravity = Gravity.CENTER
                tv.text = buttonText
                return
            }
        }
    }
    private fun loginUserWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                    Toast.makeText(this,"Google sign in failed :"+e.localizedMessage.toString(),Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,"signInWithCredential:failure",Toast.LENGTH_LONG).show()
                    updateUI(null)
                }
            }
    }



    private fun updateUI(firebaseUser: FirebaseUser?) {
        val inAccount = GoogleSignIn.getLastSignedInAccount(applicationContext)
        if (inAccount != null) {
            val personName = inAccount.displayName
            val personGivenName = inAccount.givenName
            val personFamilyName = inAccount.familyName
            val personId = inAccount.id
            val personPhoto = inAccount.photoUrl.toString()

            val userResponse = UserResponse(
                "N/F", "N/F", "male", personId, inAccount.email,
                "$personName $personFamilyName", "0123456789", personPhoto
            )
            LoginUtils.getInstance(this)!!.saveUserInfo(userResponse)
            startActivity(Intent(this, RegisterActivity::class.java).putExtra("google", "google"))
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
                }
                Resource.Status.ERROR -> {
                    val errorMessage = when (it.message?.toInt()) {
                        400 -> getString(R.string.noInternet)
                        else -> getString(R.string.serverInterrupted)
                    }
                    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()

                }
                Resource.Status.SUCCESS -> {
                    it.data.let {
                        loginResponse = it!!
                        LoginUtils.getInstance(applicationContext)!!.saveUserInfo(loginResponse)
                        LoginUtils.getInstance(applicationContext)!!.saveUserInfo(loginRequist)
                        setLogin(true)
                        goToSplashActivity()
                    }
                }
            }
        })
    }

    fun setLogin(isLogin: Boolean) {
        val sharedPreferences = this.getSharedPreferences(
            Constants.USER_SHARED_PREF,
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLogin", isLogin)
        editor.apply()
    }

    private fun goToSplashActivity() {
        startActivity(
            Intent(this@LoginActivity, SplashActivity::class.java)
        )
        finish()
    }

    fun getLogin(): Boolean {
        val sharedPreferences = this.getSharedPreferences(
            Constants.USER_SHARED_PREF,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getBoolean("isLogin", false)
    }

    fun isLoggedIn(isLogin: Boolean): Boolean {
        val sharedPreferences =
            this.getSharedPreferences(Constants.USER_SHARED_PREF, Context.MODE_PRIVATE)
        return sharedPreferences.getString("id", null) != null && isLogin
    }
}