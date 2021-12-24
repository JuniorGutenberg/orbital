package com.orbital.biblioteca.view.fragment

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.orbital.biblioteca.R
import com.orbital.biblioteca.databinding.BibliotecaFragmentBinding
import com.orbital.biblioteca.view.adapter.BibliotecaAdapter
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.BlurTransformation

class BibliotecaFragment:Fragment() {

    lateinit var binding: BibliotecaFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BibliotecaFragmentBinding.inflate(layoutInflater)

        initComponents()
        initRecycler()
        //changeStatusBar()


        return binding.root
    }
    private fun initComponents(){
        binding.apply {
            Picasso.with(context).load(R.drawable.universe_600).centerCrop().fit().into(imageView)
        }
    }
    private fun initRecycler(){
        binding.apply {
            rvOffline.isNestedScrollingEnabled = false
            rvOffline.setHasFixedSize(true)

            val layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            rvOffline.layoutManager = layoutManager

            val itemsOffline: MutableList<String> = arrayListOf("FAIXAS","PLAYLIST","PASTA")

            adapterOffline(itemsOffline)

            rvOnline.isNestedScrollingEnabled = false
            rvOnline.setHasFixedSize(true)

            val layoutManager2 = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            rvOnline.layoutManager = layoutManager2

            val itemsOnline: MutableList<String> = arrayListOf("MACHINE","PLAYLIST","ARTISTAS","HISTORICO","FAVORITOS")

            adapterOnline(itemsOnline)
        }
    }
    private fun adapterOffline(items:List<String>){
        binding.apply {
            rvOffline.adapter = context?.let { BibliotecaAdapter(items, it) }
        }
    }
    private fun adapterOnline(items:List<String>){
        binding.apply {
            rvOnline.adapter = context?.let { BibliotecaAdapter(items, it) }
        }
    }
   /* private fun changeStatusBar(){
        if (Build.VERSION.SDK_INT in 19..20) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            activity?.window?.statusBarColor = Color.TRANSPARENT
        }
    }
    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = activity?.window
        val winParams = win?.attributes
        if (on) {
            winParams?.flags = winParams?.flags?.or(bits)
        } else {
            winParams?.flags = winParams?.flags?.and(bits.inv())
        }
        win?.attributes = winParams
    }*/
}