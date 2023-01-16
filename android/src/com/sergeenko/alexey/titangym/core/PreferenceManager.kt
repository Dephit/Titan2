package com.sergeenko.alexey.titangym.core

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class PreferenceManager(private val context: Context) {

    private val preferences = context.getSharedPreferences(context.packageName, MODE_PRIVATE)

    var isMusicOn
        get() = preferences.getBoolean("isMusicOn", false)
        set(value) = preferences.edit().putBoolean("isMusicOn", value).apply()

    var player
        get() = preferences.getString("player", "")
        set(value) = preferences.edit().putString("player", value).apply()
 }