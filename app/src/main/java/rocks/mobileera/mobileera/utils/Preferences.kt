package rocks.mobileera.mobileera.utils

import android.content.Context

class Preferences {

    private val SHARED_PREFERENCES_KEY: String = "SHARED_PREFERENCES_KEY"

    fun store(context: Context, key: String, value: Boolean) {
        context.getSharedPreferences(SHARED_PREFERENCES_KEY, 0).
                edit().
                putBoolean(key, value).
                apply()
    }

    fun read(context: Context, key: String): Boolean {
        return context.getSharedPreferences(SHARED_PREFERENCES_KEY, 0).getBoolean(key, false)
    }

}