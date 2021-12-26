package com.orbital.top.view.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.VolleyError
import com.orbital.top.databinding.GenerosMainActivityBinding
import com.orbital.top.di.component.DaggerGenerosComponent
import com.orbital.top.di.module.GenerosModule
import com.orbital.top.dto.GenerosDTO
import com.orbital.top.presenter.GenerosPresent
import com.orbital.top.presenter.contract.IGenerosContract
import com.orbital.top.view.adapter.GenerosMainAdapter
import org.json.JSONException
import javax.inject.Inject

class GenerosMainActivity:AppCompatActivity(), IGenerosContract.IGenerosView {

    private lateinit var binding: GenerosMainActivityBinding
    private var nome:String?=null

    @Inject
    lateinit var present:GenerosPresent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GenerosMainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DaggerGenerosComponent
            .builder()
            .generosModule(GenerosModule(this))
            .build()
            .inject(this)

        getExtras()
        initComponents()
        initRecycler()
        callPresenter()
        changeStatusBar()
    }
    private fun callPresenter(){
        nome?.let { present.getPlaylist(this@GenerosMainActivity, it) }
    }
    private fun getExtras(){
        nome = intent.getStringExtra("nome")
    }
    private fun initComponents(){
        binding.apply {
            tv.text = nome
        }
    }
    private fun initRecycler(){
        binding.apply {
            rv.isNestedScrollingEnabled = false
            rv.setHasFixedSize(true)

            val gridLayoutManager = GridLayoutManager(this@GenerosMainActivity,2)
            rv.layoutManager = gridLayoutManager
        }
    }
    private fun changeStatusBar() {

        if (Build.VERSION.SDK_INT in 19..20) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window?.decorView?.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window?.statusBarColor = Color.TRANSPARENT
        }
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win?.attributes
        if (on) {
            winParams?.flags = winParams?.flags?.or(bits)
        } else {
            winParams?.flags = winParams?.flags?.and(bits.inv())
        }
        win?.attributes = winParams
    }

    override fun onSucess(response: List<GenerosDTO>) {
        runOnUiThread {
            binding.apply {
                rv.adapter = GenerosMainAdapter(response,this@GenerosMainActivity)
            }
        }
    }

    override fun onError(error: VolleyError) {
    }

    override fun onErrorJSON(error: JSONException) {
    }
}