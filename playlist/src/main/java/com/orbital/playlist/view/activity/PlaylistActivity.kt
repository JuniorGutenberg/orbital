package com.orbital.playlist.view.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.orbital.playlist.databinding.PlaylistActivityBinding
import com.orbital.playlist.di.component.DaggerPlaylistComponent
import com.orbital.playlist.di.module.PlaylistModule
import com.orbital.playlist.dto.OnlineDTO
import com.orbital.playlist.present.PlaylistPresent
import com.orbital.playlist.present.contract.IPlaylistContract
import com.orbital.playlist.view.adapter.PlaylistAdapter
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.BlurTransformation
import org.json.JSONException
import java.io.IOException
import javax.inject.Inject

class PlaylistActivity:AppCompatActivity(), IPlaylistContract.IPlaylistView {

    private lateinit var binding: PlaylistActivityBinding

    private var nome:String?=null
    private var image:String?=null
    private var autor:String?=null
    private var idYoutube:String?=null
    private var twitter:String?=null
    private var instagram:String?=null

    @Inject
    lateinit var present: PlaylistPresent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PlaylistActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DaggerPlaylistComponent
            .builder()
            .playlistModule(PlaylistModule(this))
            .build()
            .inject(this)

        getExtras()
        initComponents()
        initRecycler()
        changeStatusBar()
        callPresent()
    }
    private fun callPresent(){
        idYoutube?.let { present.getSongs(it,this) }
    }
    private fun getExtras(){
        nome = intent.getStringExtra("nome")
        image = intent.getStringExtra("image")
        autor = intent.getStringExtra("autor")
        idYoutube = intent.getStringExtra("idYoutube")
        twitter = intent.getStringExtra("twitter")
        instagram = intent.getStringExtra("instagram")
    }
    private fun initComponents(){
        binding.apply {
            Picasso.with(this@PlaylistActivity).load(image).centerCrop().fit()
                .transform(BlurTransformation(this@PlaylistActivity,25,2))
                .into(ivFundo)
            Picasso.with(this@PlaylistActivity).load(image).centerCrop().fit().into(riv)

            tvNome.text =   nome
            tvAutor.text    =   autor
        }
    }
    private fun initRecycler(){
        binding.apply {
            rv.isNestedScrollingEnabled = false
            rv.setHasFixedSize(true)

            val linearLayoutManager = LinearLayoutManager(this@PlaylistActivity)
            rv.layoutManager = linearLayoutManager
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

    override fun onSucess(response: List<OnlineDTO>) {
        runOnUiThread {
            binding.apply {
                rv.adapter = PlaylistAdapter(response,this@PlaylistActivity)
            }
        }
    }

    override fun onError(error: IOException) {
    }

    override fun onErrorJSON(error: JSONException) {
    }
}