package ahmed.adel.sleeem.clowyy.souq.utils

import android.content.Context
import android.content.res.Configuration
import java.util.*

class LanguageUtils {
    fun setEnglishState(context: Context, isEnglishEnabled: Boolean) {
        val sharedpreferences = context.getSharedPreferences("language_data", Context.MODE_PRIVATE)
        val editor = sharedpreferences.edit()
        editor.putBoolean("language", isEnglishEnabled)
        editor.apply()
    }

    fun getEnglishState(context: Context): Boolean {
        val sharedpreferences = context.getSharedPreferences("language_data", Context.MODE_PRIVATE)
        return sharedpreferences.getBoolean("language", true)
    }

    fun setLocale(context: Context, language: String?) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
        // Save data to shared preferences
        val editor = context.getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_lang", language)
        editor.apply()
    }

    // Load language saved in shared preferences
    fun loadLocale(context: Context) {
        val prefs = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val language = prefs.getString("My_lang", "")
        setLocale(context, language)
    }
}