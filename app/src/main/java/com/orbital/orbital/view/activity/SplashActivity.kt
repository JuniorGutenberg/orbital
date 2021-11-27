package com.orbital.orbital.view.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.orbital.core.factory.OrbitalSharedPreferencesFactory

@SuppressLint("CustomSplashScreen")
class SplashActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
        /**
        if(isCadastroStatus(applicationContext) == true){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }*/
    }
    private fun isCadastroStatus(context: Context): Boolean? {
        return OrbitalSharedPreferencesFactory.build(context).getCadastroStatus()
    }
}