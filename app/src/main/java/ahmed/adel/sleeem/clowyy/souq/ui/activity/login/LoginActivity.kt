package ahmed.adel.sleeem.clowyy.souq.ui.activity.login

import ahmed.adel.sleeem.clowyy.souq.R
import ahmed.adel.sleeem.clowyy.souq.databinding.ActivityLoginBinding
import ahmed.adel.sleeem.clowyy.souq.models.ApiManager
import ahmed.adel.sleeem.clowyy.souq.pojo.LoginRequest
import ahmed.adel.sleeem.clowyy.souq.pojo.LoginResponse
import ahmed.adel.sleeem.clowyy.souq.ui.activity.MainActivity
import ahmed.adel.sleeem.clowyy.souq.utils.Constants.SHARED_TOKEN_NAME
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.Login
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginResponse: LoginResponse
    private lateinit var token: String
    lateinit var preferences: SharedPreferences
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager
    private lateinit var buttonFacebookLogin: LoginButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        printKeyHash()

        // Initialize Firebase Auth
        auth = Firebase.auth

        signInFacebook()

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences(SHARED_TOKEN_NAME, Context.MODE_PRIVATE)
        val retrivedToken = preferences.getString("TOKEN", null)
        if (!retrivedToken.isNullOrEmpty()) {
            token = retrivedToken
            Log.e("TAG", "onCreate: ===> " + token)
        }
        binding.RegisterLoginTextView.setOnClickListener(this)
        binding.signInLoginButton.setOnClickListener(this)
        binding.googleSignIn.setOnClickListener(this)
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
                signInGoogle()
            }
        }
    }

    private fun signInFacebook() {
        // Initialize Facebook Login button
        callbackManager = CallbackManager.Factory.create()

        buttonFacebookLogin = LoginButton(this)
        buttonFacebookLogin.setReadPermissions("email", "public_profile")

//        LoginManager.getInstance()
//            .logInWithReadPermissions(this, Arrays.asList("email", "public_profile"))
        buttonFacebookLogin.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
            }
        })
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

                    Log.e("TAG", "onCreate: ===> " + response.body()!!.token)
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

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
//        if (currentUser != null)
//        {
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        }

    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.i(TAG, "signInWithCredential:success")
                    Log.i(TAG, "displayName: ${auth.currentUser?.displayName.toString()}")
                    Log.i(TAG, "email: ${auth.currentUser?.email.toString()}")
                    Log.i(TAG, "uid: ${auth.currentUser?.uid.toString()}")

//                    startActivity(Intent(this, MainActivity::class.java))

                    val user = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.i(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }

//    private fun printKeyHash() {
//        try {
//            val info = packageManager.getPackageInfo(
//                "ahmed.adel.sleeem.clowyy.souq",
//                PackageManager.GET_SIGNATURES
//            )
//            for (signature in info.signatures) {
//                val md = MessageDigest.getInstance("SHA")
//                md.update(signature.toByteArray())
//                Log.i("hash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
//            }
//        } catch (e: PackageManager.NameNotFoundException) {
//            Log.i("hash", "printKeyHash: error")
//        } catch (E: NoSuchAlgorithmException) {
//            Log.i("hash", "printKeyHash: error2")
//        }
//    }
}