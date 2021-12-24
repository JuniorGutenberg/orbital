package com.orbital.perfil.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.orbital.perfil.R
import com.orbital.perfil.databinding.PerfilFragmentBinding
import com.orbital.perfil.view.adapter.PerfilAdapter
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.BlurTransformation

class PerfilFragment:Fragment() {
    private lateinit var binding: PerfilFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PerfilFragmentBinding.inflate(layoutInflater)

        initComponents()
        initRecycler()

        return binding.root
    }
    private fun initComponents(){
        binding.apply {
            Picasso.with(context).load(R.drawable.universe_600).centerCrop().fit().into(imageView)
        }
    }
    private fun initRecycler(){
        binding.apply {
            rvSettings.isNestedScrollingEnabled = false
            rvSettings.setHasFixedSize(true)

            val layoutManager = LinearLayoutManager(context)
            rvSettings.layoutManager = layoutManager

            val itemsSettings: MutableList<String> = arrayListOf("SLEEP_TIMER","CONFIGURACOES","SOBRE")
            adapterSettings(itemsSettings)

            rvRedes.isNestedScrollingEnabled = false
            rvRedes.setHasFixedSize(true)

            val layoutManager2 = LinearLayoutManager(context)
            rvRedes.layoutManager = layoutManager2

            val itemsRedes: MutableList<String> = arrayListOf("TWITTER","INSTAGRAM")
            adapterRedes(itemsRedes)
        }
    }
    private fun adapterSettings(items:List<String>){
        binding.apply {
            rvSettings.adapter = context?.let { PerfilAdapter(items, it) }
        }
    }
    private fun adapterRedes(items:List<String>){
        binding.apply {
            rvRedes.adapter = context?.let { PerfilAdapter(items, it) }
        }
    }
}