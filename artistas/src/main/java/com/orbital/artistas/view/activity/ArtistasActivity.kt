package com.orbital.artistas.view.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.orbital.artistas.databinding.ArtistaActivityBinding
import com.orbital.artistas.di.component.DaggerListernerComponent
import com.orbital.artistas.di.module.ListernerModule
import com.orbital.artistas.dto.AlbumDTO
import com.orbital.artistas.dto.TopTrackDTO
import com.orbital.artistas.presenter.ListernerPresent
import com.orbital.artistas.presenter.contract.IListernerContract
import com.orbital.artistas.view.adapter.ArtistaRelacionadosAdapter
import com.orbital.artistas.view.adapter.TopAlbunsAdapter
import com.orbital.artistas.view.adapter.TopTrackAdapter
import com.orbital.core.utils.StringUtils
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.BlurTransformation
import org.json.JSONException
import java.io.IOException
import javax.inject.Inject

class ArtistasActivity : AppCompatActivity(), IListernerContract.IListernerView {

    private lateinit var binding: ArtistaActivityBinding
    private var image: String? = null
    private var nome: String? = null

    @Inject
    lateinit var present: ListernerPresent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ArtistaActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DaggerListernerComponent
            .builder()
            .listernerModule(ListernerModule(this))
            .build()
            .inject(this)


        getExtras()
        initComponents()
        initRecycler()
        changeStatusBar()
        getPresenter()

    }

    private fun getPresenter(){
        nome?.let { present.getListerner(this, it) }
        nome?.let { present.getTopTrack(this, it) }
        nome?.let { present.getTopAlbuns(this, it) }
        nome?.let { present.getArtistasRelacionados(this, it) }
    }

    private fun initComponents() {
        binding.apply {

            Picasso.with(this@ArtistasActivity).load(image).centerCrop().fit().into(civ)

            Picasso.with(this@ArtistasActivity).load(image).centerCrop().fit()
                .transform(BlurTransformation(this@ArtistasActivity, 25, 1))
                .into(ivFundo)
            tvArtista.text = nome?.let { StringUtils.removePlusToSpace(it) }

        }
    }

    private fun getExtras() {
        image = intent.getStringExtra("image")
        nome = intent.getStringExtra("nome")
    }
    private fun initRecycler(){
        binding.apply {
            rvTopTrack.isNestedScrollingEnabled = false
            rvTopTrack.setHasFixedSize(true)

            val llmTopTrack = LinearLayoutManager(this@ArtistasActivity)
            rvTopTrack.layoutManager    =   llmTopTrack

            rvTopAlbuns.isNestedScrollingEnabled = false
            rvTopAlbuns.setHasFixedSize(true)

            val llmTopAlbum = LinearLayoutManager(this@ArtistasActivity,LinearLayoutManager.HORIZONTAL,false)
            rvTopAlbuns.layoutManager    =   llmTopAlbum

            rvArtistaRelacionados.isNestedScrollingEnabled = false
            rvArtistaRelacionados.setHasFixedSize(true)

            val llmArtistaRelacionados= LinearLayoutManager(this@ArtistasActivity,LinearLayoutManager.HORIZONTAL,false)
            rvArtistaRelacionados.layoutManager    =   llmArtistaRelacionados
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

    override fun onSucess(response: String) {
        runOnUiThread {
            binding.apply {
                tvListerners.visibility = View.VISIBLE
                tvListerners.text = response
            }
        }
    }

    override fun onError(error: IOException) {
        runOnUiThread {
            binding.apply {
                tvListerners.visibility = View.GONE
            }
        }
    }

    override fun onErrorJSON(error: JSONException) {
        runOnUiThread {
            binding.apply {
                tvListerners.visibility = View.GONE
            }
        }
    }

    override fun onSucessTopTrack(response: List<TopTrackDTO>) {
        runOnUiThread {
            binding.apply {
                if(response.isNotEmpty()){
                    tvTopTrack.visibility   =   View.VISIBLE
                    rvTopTrack.adapter = TopTrackAdapter(response,this@ArtistasActivity)
                }
            }
        }
    }

    override fun onErrorTopTrack(error: IOException) {
        runOnUiThread {
            binding.apply {
                tvTopTrack.visibility   =   View.GONE
                rvTopTrack.visibility = View.GONE
            }
        }
    }

    override fun onErrorJSONTopTrack(error: JSONException) {
        runOnUiThread {
            binding.apply {
                tvTopTrack.visibility   =   View.GONE
                rvTopTrack.visibility = View.GONE
            }
        }
    }

    override fun onSucessTopAlbuns(response: List<AlbumDTO>) {
        runOnUiThread {
            binding.apply {
                tvTopAlbuns.visibility = View.VISIBLE
                rvTopAlbuns.adapter = TopAlbunsAdapter(response,this@ArtistasActivity)
            }
        }
    }

    override fun onErrorTopAlbuns(error: IOException) {
        runOnUiThread {
            binding.apply {
                tvTopAlbuns.visibility   =   View.GONE
                rvTopAlbuns.visibility = View.GONE
            }
        }
    }

    override fun onErrorJSONTopAlbuns(error: JSONException) {
        runOnUiThread {
            binding.apply {
                tvTopAlbuns.visibility   =   View.GONE
                rvTopAlbuns.visibility = View.GONE
            }
        }
    }

    override fun onSucessArtistasRelacionados(response: List<AlbumDTO>) {
        runOnUiThread {
            binding.apply {
                tvArtistaRelacionados.visibility = View.VISIBLE
                rvArtistaRelacionados.adapter = ArtistaRelacionadosAdapter(response,this@ArtistasActivity)
            }
        }
    }

    override fun onErrorArtistasRelacionados(error: IOException) {
        runOnUiThread {
            binding.apply {
                tvArtistaRelacionados.visibility   =   View.GONE
                rvArtistaRelacionados.visibility = View.GONE
            }
        }
    }

    override fun onErrorJSONArtistasRelacionados(error: JSONException) {
        runOnUiThread {
            binding.apply {
                tvArtistaRelacionados.visibility   =   View.GONE
                rvArtistaRelacionados.visibility = View.GONE
            }
        }
    }
}