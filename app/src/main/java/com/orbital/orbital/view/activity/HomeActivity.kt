package com.orbital.orbital.view.activity

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.viewpager.widget.ViewPager
import com.orbital.orbital.databinding.HomeActivityBinding
import com.orbital.orbital.view.adapter.ViewPagerAdapter
import com.orbital.orbital.R

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
            ivPerfil.setImageDrawable(drawablePerfil)
            ivBiblioteca.setImageDrawable(drawableBiblioteca)
            ivTop.setImageDrawable(drawableTop)
            ivParaVoce.setImageDrawable(drawableParaVoce)
            ivBuscar.setImageDrawable(drawableBuscar)
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
        binding.rlPerfil.setOnClickListener {
            binding.viewPager.currentItem = 0
        }

        binding.rlBiblioteca.setOnClickListener {
            binding.viewPager.currentItem = 1
        }

        binding.rlTop.setOnClickListener {
            binding.viewPager.currentItem = 2
        }
        binding.rlParaVoce.setOnClickListener {
            binding.viewPager.currentItem = 3
        }

        binding.rlBuscar.setOnClickListener {
            binding.viewPager.currentItem = 4
        }
    }
    private fun changeColor(position : Int){
        val text: ArrayList<TextView> = arrayListOf(binding.tvPerfil,binding.tvBiblioteca,binding.tvTop,binding.tvParaVoce,binding.tvBuscar)
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
                val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
                binding.viewPager.adapter = viewPagerAdapter
                binding.viewPager.currentItem = 2
                val handlerVP = Handler(mainLooper)
                handlerVP.post {
                    binding.viewPager.offscreenPageLimit = 3
                }
            }
        }.start()
    }
    private fun hideToolbar() {
        binding.toolbarNewMain.clearAnimation()
        binding.toolbarNewMain.animate().translationY(binding.toolbarNewMain.height.toFloat()).duration = 200
    }
    private fun showToolbar() {
        binding.toolbarNewMain.clearAnimation()
        binding.toolbarNewMain.animate().translationY(0f).duration = 200
    }
}