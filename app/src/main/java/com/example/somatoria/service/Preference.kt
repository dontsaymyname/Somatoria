package com.example.somatoria.service

import android.content.Context
import android.content.SharedPreferences

class Preference(context: Context) {

    companion object {
        const val NUMBER_KEY = "number"
    }

    private val mPreference: SharedPreferences =
        context.getSharedPreferences("baseNumber", Context.MODE_PRIVATE)

    fun saveString(key: String, number: String) {
        mPreference.edit().putString(key, number).apply()
    }

    fun getString(key: String): String? {
        return mPreference.getString(key, "")
    }
}