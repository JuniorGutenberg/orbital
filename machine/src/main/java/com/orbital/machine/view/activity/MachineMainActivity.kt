package com.orbital.machine.view.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.orbital.core.utils.KeyboardUtils
import com.orbital.core.utils.ViewUtils
import com.orbital.machine.R
import com.orbital.machine.databinding.MachineMainActivityBinding
import com.orbital.machine.di.components.DaggerMachineMainComponent
import com.orbital.machine.di.module.MachineMainModule
import com.orbital.machine.dto.ArtistaDTO
import com.orbital.machine.dto.TracksDTO
import com.orbital.machine.present.MachineMainPresent
import com.orbital.machine.present.contract.IMachineMainContract
import com.orbital.machine.view.adapter.SearchArtistAdapter
import com.squareup.picasso.Picasso
import org.json.JSONException
import java.io.*
import java.lang.Exception
import javax.inject.Inject

class MachineMainActivity: AppCompatActivity(), IMachineMainContract.IMachineMainView {

    private lateinit var binding: MachineMainActivityBinding



    @Inject
    lateinit var present: MachineMainPresent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MachineMainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DaggerMachineMainComponent
            .builder()
            .machineMainModule(MachineMainModule(this))
            .build()
            .inject(this)


        //saveOrbitalMachine(arrayListOf())

        initComponents()
        initRecycler()
        clickAutoComplete()
        changeStatusBar()
        changeDrawable()
    }
    private fun searchArtist(key:String){
        runOnUiThread {
            binding.apply {
                ViewUtils.loading(this@MachineMainActivity)
                orls.desativar(R.drawable.back_buscar_desativado)
                rlCheck.desativar(R.drawable.back_circular_desativado)
                limparRecycler()
            }
        }
        present.searchArtist(key,this)
    }
    private fun limparRecycler(){
        binding.apply {
            if(rvArtistas.adapter != null){
                rvArtistas.adapter = null
            }
        }
    }

    private fun initComponents(){
        binding.apply {
            Picasso.with(this@MachineMainActivity).load(R.drawable.universe_600).centerCrop().fit().into(ivFundo)
            rlCheck.desativar(R.drawable.back_circular_desativado)
        }
    }
    private fun initRecycler(){
        binding.apply {
            rvArtistas.isNestedScrollingEnabled = false
            rvArtistas.setHasFixedSize(true)

            val gridLayoutManager = GridLayoutManager(this@MachineMainActivity,2)

            rvArtistas.layoutManager = gridLayoutManager

            rvSelecionados.isNestedScrollingEnabled = false
            rvSelecionados.setHasFixedSize(true)

            val linearLayoutManager = LinearLayoutManager(this@MachineMainActivity,LinearLayoutManager.HORIZONTAL,false)

            rvSelecionados.layoutManager = linearLayoutManager
        }
    }
    private fun clickAutoComplete(){
        runOnUiThread {
            binding.apply {
                autoComplete.setOnItemClickListener { _, _, _, _ ->
                    KeyboardUtils.hideKeyboard(this@MachineMainActivity)
                    searchArtist(autoComplete.text.toString())
                }

                autoComplete.setOnEditorActionListener { _, p1, _ ->
                    if (p1 == EditorInfo.IME_ACTION_SEARCH) {
                        KeyboardUtils.hideKeyboard(this@MachineMainActivity)
                        autoComplete.clearFocus()
                        searchArtist(autoComplete.text.toString())
                    }
                    false
                }
            }
        }
    }
    private fun changeDrawable(){
        binding.apply {
            ivBuscar.setImageDrawable(AppCompatResources.getDrawable(this@MachineMainActivity, R.drawable.ic_search) )
            ivBuscar.setColorFilter(R.color.black)
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

    override fun onSucess(response: List<ArtistaDTO>,number:Int) {
        if(response.size >= number){
            runOnUiThread {
                binding.apply {
                    val itemsSelecionados: MutableList<ArtistaDTO> = arrayListOf()
                    rvArtistas.adapter = SearchArtistAdapter(response,this@MachineMainActivity,binding,itemsSelecionados,present)
                    ViewUtils.dismissLoading()
                    orls.ativar(R.drawable.back_buscar)
                }
            }
        }
    }

    override fun onError(error: IOException) {
    }

    override fun onErrorJSON(error: JSONException) {
    }


    override fun onSucessMachineResult(response: List<TracksDTO>, number: Int) {
        Log.e("onSucess","Machine Result")
        runOnUiThread {
            val list:MutableList<String> = arrayListOf()
            list.addAll(readOrbitalMachine())
            val  nome = "Machine "+(list.size+1)
            list.add(nome)
            saveOrbitalMachine(list)
            saveOrbitalMachineItems(response,nome)
            ViewUtils.dismissLoading()
            finish()
        }
    }

    override fun onErrorMachineResult(error: IOException) {
    }

    override fun onErrorJSONMachineResult(error: JSONException) {
    }

    private fun saveOrbitalMachine(arrayList: List<String>) {
        try {
            val fos = openFileOutput("MACHINE", MODE_PRIVATE)
            val of = ObjectOutputStream(fos)
            of.writeObject(arrayList)
            of.flush()
            of.close()
            fos.close()
        } catch (e: Exception) {
            Log.e("InternalStorage", e.message!!)
        }
    }
    private fun readOrbitalMachine(): List<String> {
        var toReturn: List<String> = arrayListOf()
        val fis: FileInputStream
        try {
            fis = openFileInput("MACHINE")
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
    private fun saveOrbitalMachineItems(arrayList: List<TracksDTO>, nome: String) {
        try {
            val fos = openFileOutput(nome, MODE_PRIVATE)
            val of = ObjectOutputStream(fos)
            of.writeObject(arrayList)
            of.flush()
            of.close()
            fos.close()
        } catch (e: Exception) {
            Log.e("InternalStorage", e.message!!)
        }
    }

}