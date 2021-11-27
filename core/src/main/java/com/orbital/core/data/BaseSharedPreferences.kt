package com.orbital.core.data

import android.content.Context
import android.content.SharedPreferences

abstract class BaseSharedPreferences {
    private var basePreferences: SharedPreferences? = null

    open fun BaseSharedPreferences(name: String?, context: Context) {
        basePreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    protected open fun edit(): SharedPreferences.Editor? {
        return basePreferences!!.edit()
    }

    protected open fun get(): SharedPreferences? {
        return basePreferences
    }
}