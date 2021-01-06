package com.hasandeniz.reminderassistant.data

import android.content.Context
import androidx.preference.PreferenceManager

class MyPreferences(context: Context?) {

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    var darkMode = preferences.getInt("a", 0)
        set(value) = preferences.edit().putInt("a", value).apply()

}