package com.orbital.core.factory

import android.content.Context
import com.orbital.core.R

class OrbitalSharedPreferencesFactory {
    companion object{
        fun build(context: Context):OrbitalSharedPreferences{

            return  OrbitalSharedPreferences(context.packageName+context.getString(R.string.app_name),context)
        }
    }
}