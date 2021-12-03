package com.orbital.orbital.view.activity

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.viewpager.widget.ViewPager
import com.orbital.orbital.databinding.HomeActivityBinding
import com.orbital.orbital.R
import com.orbital.orbital.view.adapter.ViewPagerAdapter
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.BlurTransformation

class HomeActivity: AppCompatActivity() {
    private lateinit var binding: HomeActivityBinding

    var oneTime = false
    private var drawablePerfil: Drawable? = null
    private var drawableBiblioteca: Drawable? = null
    private var drawableTop: Drawable? = null
    private var drawableParaVoce: Drawable? = null
    private var drawableBuscar: Drawable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)



        setDrawable()
        initViewPager()
        setScrollViewPager()
        onClickRelative()
        initComponents()
        changeStatusBar()
    }
    private fun initComponents(){
        binding.apply {
            toolbarPlayer.visibility = View.GONE
        }
    }
    private fun setDrawable(){
        drawablePerfil = AppCompatResources.getDrawable(this, R.drawable.ic_perfil)
        drawableBiblioteca = AppCompatResources.getDrawable(this, R.drawable.ic_library)
        drawableTop = AppCompatResources.getDrawable(this, R.drawable.ic_fire)
        drawableParaVoce = AppCompatResources.getDrawable(this, R.drawable.ic_heart)
        drawableBuscar = AppCompatResources.getDrawable(this, R.drawable.ic_search)

        binding.apply {
            toolbarNewMain.ivPerfil.setImageDrawable(drawablePerfil)
            toolbarNewMain.ivBiblioteca.setImageDrawable(drawableBiblioteca)
            toolbarNewMain.ivTop.setImageDrawable(drawableTop)
            toolbarNewMain.ivParaVoce.setImageDrawable(drawableParaVoce)
            toolbarNewMain.ivBuscar.setImageDrawable(drawableBuscar)
        }
    }
    private fun setScrollViewPager(){
        binding.apply {
            viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    if(!oneTime){
                        changeColor(position)
                        oneTime = true
                    }
                }

                override fun onPageSelected(position: Int) {
                    showToolbar()
                    changeColor(position)
                }

                override fun onPageScrollStateChanged(state: Int) {
                    Log.i(this.javaClass.name,"")
                }
            })
        }
    }
    private fun onClickRelative(){
        binding.toolbarNewMain.rlPerfil.setOnClickListener {
            binding.viewPager.currentItem = 0
        }

        binding.toolbarNewMain.rlBiblioteca.setOnClickListener {
            binding.viewPager.currentItem = 1
        }

        binding.toolbarNewMain.rlTop.setOnClickListener {
            binding.viewPager.currentItem = 2
        }
        binding.toolbarNewMain.rlParaVoce.setOnClickListener {
            binding.viewPager.currentItem = 3
        }

        binding.toolbarNewMain.rlBuscar.setOnClickListener {
            binding.viewPager.currentItem = 4
        }
    }
    private fun changeColor(position : Int){
        val text: ArrayList<TextView> = arrayListOf(binding.toolbarNewMain.tvPerfil,binding.toolbarNewMain.tvBiblioteca,
            binding.toolbarNewMain.tvTop,binding.toolbarNewMain.tvParaVoce,binding.toolbarNewMain.tvBuscar)
        val drawable: ArrayList<Drawable> = arrayListOf(drawablePerfil!!,drawableBiblioteca!!,drawableTop!!,drawableParaVoce!!,drawableBuscar!!)
        for (i in 0 until 5) {
            if(i == position){
                text[i].setTextColor(ContextCompat.getColor(this,R.color.principal))
                drawable[i].colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                    ContextCompat.getColor(this,R.color.principal),
                    BlendModeCompat.SRC_ATOP)
            }else{
                text[i].setTextColor(ContextCompat.getColor(this,android.R.color.white))
                drawable[i].colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                    ContextCompat.getColor(this,android.R.color.white),
                    BlendModeCompat.SRC_ATOP)
            }
        }
    }
    private fun initViewPager(){
        object : Thread() {
            override fun run() {
                super.run()
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    val layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                    )
                    layoutParams.setMargins(0, 0, 0, 110)
                    binding.viewPager.layoutParams = layoutParams
                }
                val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager,binding.toolbarNewMain.toolbarNewMain)

                binding.viewPager.adapter = viewPagerAdapter
                binding.viewPager.currentItem = 2
                val handlerVP = Handler(mainLooper)
                handlerVP.post {
                    binding.viewPager.offscreenPageLimit = 3
                }
            }
        }.start()
    }
    private fun changeStatusBar(){

        if (Build.VERSION.SDK_INT in 19..20) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
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
    private fun showToolbar() {
        binding.toolbarNewMain.toolbarNewMain.clearAnimation()
        binding.toolbarNewMain.toolbarNewMain.animate().translationY(0f).duration = 200
    }
}