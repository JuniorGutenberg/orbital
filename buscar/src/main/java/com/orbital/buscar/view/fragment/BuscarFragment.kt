package com.orbital.buscar.view.fragment

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.orbital.app_navigator.view.activity.ToolbarActivity
import com.orbital.buscar.R
import com.orbital.buscar.databinding.BuscarFragmentBinding
import com.orbital.buscar.di.component.DaggerSongsLocalComponent
import com.orbital.buscar.di.module.SongsLocalModule
import com.orbital.buscar.dto.ArtistaDTO
import com.orbital.buscar.dto.OnlineDTO
import com.orbital.buscar.dto.SongsLocalDTO
import com.orbital.buscar.present.SongsLocalPresent
import com.orbital.buscar.present.contract.ISongsLocalContract
import com.orbital.buscar.view.adapter.ArtistaAdapter
import com.orbital.buscar.view.adapter.OnlineAdapter
import com.orbital.buscar.view.adapter.SongsLocalAdapter
import com.orbital.buscar.view.async.AutoCompletAsync
import com.orbital.core.utils.KeyboardUtils
import com.squareup.picasso.Picasso
import org.json.JSONException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class BuscarFragment(private var toolbar: Toolbar):Fragment(), ISongsLocalContract.ISongsLocalView {
    private lateinit var binding: BuscarFragmentBinding

    @Inject
    lateinit var present: SongsLocalPresent

    private var bScrollView = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BuscarFragmentBinding.inflate(layoutInflater)

        DaggerSongsLocalComponent
            .builder()
            .songsLocalModule(SongsLocalModule(this))
            .build()
            .inject(this)

        initComponents()
        initAutoComplete()
        initNestedScroll()

        return binding.root
    }
    private fun initComponents(){
        binding.apply {
            Picasso.with(context).load(R.drawable.universe_600).centerCrop().fit().into(imageView)
            changeColorDrawable()

            ivBuscar.setOnClickListener {
                if(autoComplete.text.isNotEmpty()){
                    pesquisar(autoComplete.text.toString())
                    activity?.let { it1 -> KeyboardUtils.hideKeyboard(it1) }
                }
            }
            tvPermission.setOnClickListener {
                activity?.let { it1 -> ActivityCompat.requestPermissions(it1, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 101) }
            }
            initRecyclers()

        }
    }
    private fun initRecyclers(){
        binding.apply {
            rvSuasMusicas.isNestedScrollingEnabled = false
            rvSuasMusicas.setHasFixedSize(true)

            val linearLayoutManagerSuasMusicas = LinearLayoutManager(context)

            rvSuasMusicas.layoutManager = linearLayoutManagerSuasMusicas

            rvOnline.isNestedScrollingEnabled = false
            rvOnline.setHasFixedSize(true)

            val linearLayoutManagerOnline = LinearLayoutManager(context)
            rvOnline.layoutManager = linearLayoutManagerOnline

            rvRelacionadas.isNestedScrollingEnabled = false
            rvRelacionadas.setHasFixedSize(true)

            val linearLayoutManagerRelacionados = LinearLayoutManager(context)
            rvRelacionadas.layoutManager = linearLayoutManagerRelacionados

            rvArtistas.isNestedScrollingEnabled = false
            rvArtistas.setHasFixedSize(true)

            val linearLayoutManagerArtistas = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            rvArtistas.layoutManager = linearLayoutManagerArtistas
        }
    }
    private fun changeColorDrawable(){
        binding.apply {

            ivBuscar.setImageDrawable(context?.let { AppCompatResources.getDrawable(it, R.drawable.ic_search) })
            val color:Int = R.color.black
            ivBuscar.setColorFilter(color)
        }
    }
    private fun initAutoComplete(){
        val thread = Thread{
            binding.apply {
                autoComplete.addTextChangedListener(object : TextWatcher{
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        var url =
                            "http://suggestqueries.google.com/complete/search?client=firefox&hl=en&ds=yt"
                        try {
                            url += "&q="+ autoComplete.text
                        } catch (e: Exception) {
                            e.message?.let { Log.e("ErrorComplet: ", it) }
                        }
                        AutoCompletAsync(url).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get()
                        autoComplete.threshold = 1
                        autoComplete.setAdapter(context?.let { ArrayAdapter<String>(it,R.layout.complete_item,AutoCompletAsync(url).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get()) })

                    }

                    override fun afterTextChanged(p0: Editable?) {
                    }
                })
            }
        }
        thread.start()
        clickAutoComplete()
    }
    private fun clickAutoComplete(){
        activity?.runOnUiThread {
            binding.apply {
                autoComplete.setOnItemClickListener { _, _, _, _ ->
                    activity?.let { it1 -> KeyboardUtils.hideKeyboard(it1) }
                    pesquisar(autoComplete.text.toString())
                }

                autoComplete.setOnEditorActionListener(object : TextView.OnEditorActionListener{
                    override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                        if(p1 == EditorInfo.IME_ACTION_SEARCH){
                            activity?.let { it1 -> KeyboardUtils.hideKeyboard(it1) }
                            autoComplete.clearFocus()
                            pesquisar(autoComplete.text.toString())
                        }
                        return false
                    }
                })
            }
        }
    }
    private fun pesquisar(key:String){
        activity?.let {
                it1 -> present.getSongsLocal(key, it1)
        }
        activity?.let { it1 -> present.getOnline(key, it1) }
        activity?.let { it1 -> present.getArtista(key, it1) }

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

    override fun onSucess(response: List<SongsLocalDTO>) {
        Log.e("onSucess","sucesso")
        activity?.runOnUiThread {
            binding.apply {
                tvPermission.visibility = View.GONE
                rvSuasMusicas.adapter = context?.let { SongsLocalAdapter(response, it) }
            }
        }
    }

    override fun onError() {
        Log.e("Error Local","Error local")
        activity?.let { checkPermissionLocal(it) }

    }

    override fun onSucessOnline(response: List<OnlineDTO>) {
        activity?.runOnUiThread {
            binding.apply {
                rvOnline.adapter = context?.let { OnlineAdapter(response, it) }
            }
        }
    }

    override fun onErrorOnline(error: IOException) {
    }

    override fun onErrorJSONOnline(error: JSONException) {
    }

    override fun onSucessRelacionados(response: List<OnlineDTO>) {
        activity?.runOnUiThread {
            binding.apply {
                rvRelacionadas.adapter = context?.let { OnlineAdapter(response, it) }
            }
        }
    }

    override fun onErrorRelacionados(error: IOException) {
    }

    override fun onErrorJSONRelacionados(error: JSONException) {
    }

    override fun onSucessArtista(response: List<ArtistaDTO>) {
        activity?.runOnUiThread {
            binding.apply {
                rvArtistas.adapter = context?.let { ArtistaAdapter(response, it) }
            }
        }
    }

    override fun onErrorArtista(error: IOException) {
    }

    override fun onErrorJSONArtista(error: JSONException) {
    }

    private fun checkPermissionLocal(activity: Activity){
        if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
           activity.runOnUiThread {
               binding.tvPermission.visibility = View.VISIBLE

           }

        }else{
            activity.runOnUiThread {
                binding.tvPermission.visibility = View.GONE

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 101){
            activity?.runOnUiThread {
                binding.tvPermission.visibility = View.GONE

            }
        }
    }
    /**
     * Levar para Core
     * private fun checkPermission(permission: String, requestCode: Int, activity: Activity) {
        if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_DENIED) {
            Log.e("Permission","Not Permission")

            // Requesting the permission
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
        } else {
            Toast.makeText(activity, "Permição ja concedida", Toast.LENGTH_SHORT).show()
        }
    }*/
}