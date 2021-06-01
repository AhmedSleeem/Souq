package ahmed.adel.sleeem.clowyy.souq.utils

import ahmed.adel.sleeem.clowyy.souq.pojo.*
import ahmed.adel.sleeem.clowyy.souq.utils.Constants.USER_SHARED_PREF
import android.content.Context

class LoginUtils private constructor(private val mCtx: Context) {

    companion object {
        private var mInstance: LoginUtils? = null

        @Synchronized
        fun getInstance(mCtx: Context): LoginUtils? {
            if (mInstance == null) {
                mInstance = LoginUtils(mCtx)
            }
            return mInstance
        }
    }

    fun saveUserInfo(response: RegisterResponse) {
        val sharedPreferences = mCtx.getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("id", response._id)
        editor.putString("name", response.name)
        editor.putString("email", response.email)
        editor.putString("profileImage", response.profileImage)
        editor.apply()
    }

    fun saveUserInfo(loginResponse: LoginResponse) {
        val sharedPreferences = mCtx.getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("error", loginResponse.error)
        editor.putString("result", loginResponse.result)
        editor.putString("token", loginResponse.token)
        editor.putString("id", loginResponse.id)
        editor.apply()
    }

    fun saveUserInfo(loginRequest: LoginRequest) {
        val sharedPreferences = mCtx.getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("email", loginRequest.email)
        editor.putString("password", loginRequest.password)
        editor.apply()
    }

    fun isLoggedIn(isLogin: Boolean): Boolean {
        val sharedPreferences =
            mCtx.getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE)
        return sharedPreferences.getString("id", null) != null && isLogin
    }

    fun saveUserInfo(userRespons: UserResponse) {
        val sharedPreferences = mCtx.getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("id", userRespons._id)
        editor.putString("name", userRespons.name)
        editor.putString("email", userRespons.email)
        editor.putString("phone", userRespons.phoneNumber)
        editor.putString("birthDay", userRespons.BirthDate)
        editor.putString("gender", userRespons.Gender)
        editor.putString("Address", userRespons.Address)
        editor.putString("profileImage", userRespons.profileImage)
        editor.apply()
    }

    fun saveToken(token: String) {
        val sharedPreferences = mCtx.getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("token", token)
        editor.apply()
    }

    fun setLogin(isLogin: Boolean) {
        val sharedPreferences = mCtx.getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLogin", isLogin)
        editor.apply()
    }

    fun getLogin(): Boolean {
        val sharedPreferences = mCtx.getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLogin", false)
    }

    fun updateGender(gender: String) {
        val sharedPreferences = mCtx.getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("gender", gender)
        editor.apply()
    }

    fun updateBirthDay(birthDay: String) {
        val sharedPreferences = mCtx.getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("birthDay", birthDay)
        editor.apply()
    }

    fun updateEmail(email: String) {
        val sharedPreferences = mCtx.getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.apply()
    }

    fun updatePhone(phone: String) {
        val sharedPreferences = mCtx.getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("phone", phone)
        editor.apply()
    }

    fun updatePassword(password: String) {
        val sharedPreferences = mCtx.getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("password", password)
        editor.apply()
    }

    fun updateName(name: String) {
        val sharedPreferences = mCtx.getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("name", name)
        editor.apply()
    }
    fun updateAddress(address: String) {
        val sharedPreferences = mCtx.getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("Address", address)
        editor.apply()
    }
    fun getUserRequist(): UserRequist {
        val sharedPreferences =
            mCtx.getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE)
        return UserRequist(
            sharedPreferences.getString("Address", null),
            sharedPreferences.getString("birthDay", null),
            sharedPreferences.getString("gender", null),
            sharedPreferences.getString("email", null),
            sharedPreferences.getString("name", null),
            sharedPreferences.getString("phone", null),
            sharedPreferences.getString("profileImage", null)
        )
    }

    fun userInfo(): FullUserInfo {
        val sharedPreferences =
            mCtx.getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE)
        return FullUserInfo(
            sharedPreferences.getString("id", null),
            sharedPreferences.getString("email", null),
            sharedPreferences.getString("name", null),
            sharedPreferences.getString("error", null),
            sharedPreferences.getString("result", null),
            sharedPreferences.getString("token", null),
            sharedPreferences.getString("phone", null),
            sharedPreferences.getString("Address", null),
            sharedPreferences.getString("birthDay", null),
            sharedPreferences.getString("gender", null),
            sharedPreferences.getString("password", null),
            sharedPreferences.getString("profileImage", null)
        )
    }

    fun clearAll() {
        val sharedPreferences = mCtx.getSharedPreferences(USER_SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear().apply()
        editor.apply()
    }
}