package com.orbital.para_voce.view.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.orbital.app_navigator.view.activity.ToolbarActivity
import com.orbital.para_voce.R
import com.orbital.para_voce.databinding.ParaVoceFragmentBinding
import com.orbital.para_voce.di.component.DaggerArtistaComponent
import com.orbital.para_voce.di.module.ArtistaModule
import com.orbital.para_voce.dto.ArtistaDTO
import com.orbital.para_voce.dto.TocadasDTO
import com.orbital.para_voce.presenter.ArtistaPresent
import com.orbital.para_voce.presenter.contract.IArtistaContract
import com.orbital.para_voce.view.adapter.ArtistaAdapter
import com.orbital.para_voce.view.adapter.MachineAdapter
import com.orbital.para_voce.view.adapter.TocadasAdapter
import com.squareup.picasso.Picasso
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.ObjectInputStream
import javax.inject.Inject

class ParaVoceFragment(private var toolbar: Toolbar): Fragment(), IArtistaContract.ArtistaView {
    private lateinit var binding: ParaVoceFragmentBinding

    @Inject
    lateinit var present: ArtistaPresent

    private var bScrollView = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ParaVoceFragmentBinding.inflate(layoutInflater)

        DaggerArtistaComponent.builder()
            .artistaModule(ArtistaModule(this))
            .build()
            .inject(this)

        activity?.let { present.getArtistas(it) }

        initComponents()
        initRecycler()
        initNestedScroll()

        return binding.root
    }
    private fun initComponents(){
        binding.apply {
            Picasso.with(context).load(R.drawable.universe_600).centerCrop().fit().into(imageView)
        }
    }
    private fun initRecycler(){
        binding.apply {
            rvArtistas.isNestedScrollingEnabled = false
            rvArtistas.setHasFixedSize(true)

            val layoutManagerArtista = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            rvArtistas.layoutManager = layoutManagerArtista

            rvMachine.isNestedScrollingEnabled = false
            rvMachine.setHasFixedSize(true)

            val layoutManagerMachine = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            rvMachine.layoutManager = layoutManagerMachine

            getMachine()

            rvTocadas.isNestedScrollingEnabled = false
            rvTocadas.setHasFixedSize(true)

            val layoutManagerTocadas = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            rvTocadas.layoutManager = layoutManagerTocadas

            getTocadas()
        }
    }
    private fun initNestedScroll(){
        binding.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                nestedScrollViewFragmentTOP.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
                    if (scrollY > oldScrollY && bScrollView) {
                        val toolbarActivity = ToolbarActivity()
                        toolbarActivity.hideToolbar(toolbar)
                    }
                    if (scrollY < oldScrollY && bScrollView) {
                        val toolbarActivity = ToolbarActivity()
                        toolbarActivity.showToolbar(toolbar)
                    }
                    if (oldScrollY == 0 && !bScrollView) {
                        val toolbarActivity = ToolbarActivity()
                        toolbarActivity.showToolbar(toolbar)
                        bScrollView = true
                    }
                })
            }
        }
    }

    private fun getMachine(){
        activity?.runOnUiThread {
            binding.apply {
                rvMachine.adapter = context?.let { MachineAdapter(readOrbitalMachine(), it) }
            }
        }
    }
    private fun getTocadas(){
        activity?.runOnUiThread {
            binding.apply {
                rvTocadas.adapter = context?.let { TocadasAdapter(readTocadas(), it) }
            }
        }
    }
    private fun readOrbitalMachine(): List<String> {
        var toReturn:List<String> = arrayListOf("ENVOLVER","PAGODE")
        val fis: FileInputStream
        try {
            fis = requireContext().openFileInput("MACHINE")
            val oi = ObjectInputStream(fis)
            toReturn = oi.readObject() as List<String>
            oi.close()
        } catch (e: FileNotFoundException) {
            Log.e("InternalStorage", e.message!!)
        } catch (e: IOException) {
            Log.e("InternalStorage", e.message!!)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        return toReturn
    }
    private fun readTocadas(): List<TocadasDTO> {
        var toReturn:MutableList<TocadasDTO> = arrayListOf()
        toReturn.add(TocadasDTO("", "", arrayListOf(),
            "","","https://storage.googleapis.com/ca082dae69f84a84997fa3c934283f8d/bb4f92ae-3fe7-4a67-9624-135895e81ac2/brazil.png",
            "Album do Brasil",
            "Brasil","","","",
            "","","","CATEGORY_ALBUM"))

        toReturn.add(TocadasDTO("PAGODE", "Machine",arrayListOf(),
            "","","",
            "",
            "","","","",
            "","","","CATEGORY_MACHINE"))
        toReturn.add(TocadasDTO("", "",arrayListOf(),
            "https://lastfm.freetls.fastly.net/i/u/ar0/4c77c30ff3b640221d794ee845b5a37e.jpg","Xamã","",
            "",
            "","","","",
            "","","","CATEGORY_ARTISTA"))
        toReturn.add(TocadasDTO("", "",arrayListOf(),
            "","","",
            "",
            "","https://storage.googleapis.com/ca082dae69f84a84997fa3c934283f8d/1faf21d8-80db-4ebb-a215-35bbf9629d1f/edm.png",
            "EDM","",
            "","","","CATEGORY_PLAYLIST"))
        val fis: FileInputStream
        try {
            fis = requireContext().openFileInput("TOCADAS")
            val oi = ObjectInputStream(fis)
            toReturn = oi.readObject() as MutableList<TocadasDTO>
            oi.close()
        } catch (e: FileNotFoundException) {
            Log.e("InternalStorage", e.message!!)
        } catch (e: IOException) {
            Log.e("InternalStorage", e.message!!)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        return toReturn
    }
    override fun onSucess(response: List<ArtistaDTO>) {
        activity?.runOnUiThread {
            binding.apply {
                rvArtistas.adapter = context?.let { ArtistaAdapter(response, it) }
            }
        }
    }

    override fun onError() {
        Log.e("onError","Artistas")

    }
}