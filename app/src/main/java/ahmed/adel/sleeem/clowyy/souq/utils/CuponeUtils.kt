package ahmed.adel.sleeem.clowyy.souq.utils

import ahmed.adel.sleeem.clowyy.souq.pojo.Cupone
import android.content.Context
import android.content.SharedPreferences

class CuponeUtils(private val context: Context) {


    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(Constants.CUPONE_SHARED_PREF,Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun editCupone(cupone :Cupone){
        editor.putString("CuponeCode" , cupone.cuponeCode)
        editor.putInt("CuponeCodeValue" , cupone.cuponeCodeValue)
        editor.apply()
    }

    fun getCupone():Cupone?{
        val code = sharedPreferences.getString("CuponeCode" , null)
        val value = sharedPreferences.getInt("CuponeCodeValue" , 0)
        if (code == null){
            return null
        }
       return Cupone(code , value)
    }

}