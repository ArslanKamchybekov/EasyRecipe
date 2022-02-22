package kg.geektech.easyrecipe.prefs

import android.content.SharedPreferences

class Prefs {

    private var preferences: SharedPreferences? = null

    fun saveBoardState() {
        preferences!!.edit().putBoolean("isShown", true).apply()
    }

    val isBoardShown: Boolean
        get() = preferences!!.getBoolean("isShown", false)
}