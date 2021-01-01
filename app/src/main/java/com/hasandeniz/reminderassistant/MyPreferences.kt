package com.hasandeniz.reminderassistant

import android.content.Context
import androidx.preference.PreferenceManager

class MyPreferences(context: Context?) {

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    var darkMode = preferences.getInt("a", 0)
        set(value) = preferences.edit().putInt("a", value).apply()

}

class MyCounterPreferences(context: Context?){
    private val myPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    var globalCounter = myPreferences.getInt("counter",0)
        set(value) = myPreferences.edit().putInt("counter",value).apply()

}