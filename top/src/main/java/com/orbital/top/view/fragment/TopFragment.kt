package com.orbital.top.view.fragment

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.VolleyError
import com.orbital.top.R
import com.orbital.top.databinding.TopFragmentBinding
import com.orbital.top.di.component.DaggerTopArtistaComponent
import com.orbital.top.di.module.TopArtistaModule
import com.orbital.top.dto.BannersDTO
import com.orbital.top.dto.PaisesDTO
import com.orbital.top.dto.TopArtistasDTO
import com.orbital.top.presenter.TopArtistaPresent
import com.orbital.top.presenter.contract.ITopArtistaContract
import com.orbital.top.view.adapter.BannersAdapter
import com.orbital.top.view.adapter.GenerosMomentosAdapter
import com.orbital.top.view.adapter.PaisesAdapter
import com.orbital.top.view.adapter.TopArtistaAdapter
import com.squareup.picasso.Picasso
import org.json.JSONException
import javax.inject.Inject
import androidx.core.widget.NestedScrollView
import androidx.viewpager.widget.ViewPager
import com.orbital.app_navigator.view.activity.ToolbarActivity
import jp.wasabeef.picasso.transformations.BlurTransformation


class TopFragment(private var toolbar: Toolbar):Fragment(), ITopArtistaContract.ITopArtistaView{

    private lateinit var binding: TopFragmentBinding
    @Inject
    lateinit var present: TopArtistaPresent

    var bScrollView = false
    var positionBanner = 0

    /**
     * Limpar as Strings
     * */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TopFragmentBinding.inflate(layoutInflater)
        initComponents()

        DaggerTopArtistaComponent
            .builder()
            .topArtistaModule(TopArtistaModule(this))
            .build()
            .inject(this)


        activity?.let {
            binding.loadingLayout.visibility = View.VISIBLE
            binding.recyclerViewTopArtistas.visibility = View.GONE
            present.getBanners(it)
            present.getGenerosMomentos(it)
            present.getPaises(it)
            present.getTopArtista(it)
        }
        return binding.root
    }
    private fun initComponents(){
        binding.apply {
            Picasso.with(context).load(R.drawable.universe_600).transform(BlurTransformation(context,4,1)).fit().centerCrop().into(imageView)
            initRecylces()
            initNestedScroll()
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

    private fun initRecylces(){
        binding.apply {
            val linearLayoutManagerHorizontal = LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false
            )

            recyclerViewTopArtistas.layoutManager = linearLayoutManagerHorizontal
            recyclerViewTopArtistas.isNestedScrollingEnabled = false
            recyclerViewTopArtistas.setHasFixedSize(true)

            val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(
                context, 2
            )

            rvGeneros.layoutManager = layoutManager
            rvGeneros.isNestedScrollingEnabled = false
            rvGeneros.setHasFixedSize(true)
            rvGeneros.setItemViewCacheSize(20)

            val layoutManagerPaises: RecyclerView.LayoutManager = GridLayoutManager(
                context, 2
            )

            rvPaises.layoutManager = layoutManagerPaises
            rvPaises.isNestedScrollingEnabled = false
            rvPaises.setHasFixedSize(true)
            rvPaises.setItemViewCacheSize(20)
        }
    }

    private fun mudarBanners(){
        val handler2 = Handler()
        val runnable: Runnable = object : Runnable {
            override fun run() {
                if (positionBanner + 1 == 3) {
                    positionBanner = -1
                }
                binding.vp.setCurrentItem(positionBanner + 1, true)
                handler2.postDelayed(this, 8000)
            }
        }
        handler2.postDelayed(runnable,800)
    }

    override fun onSucessTop(response: List<TopArtistasDTO>) {
        activity?.runOnUiThread {
            binding.apply {
                loadingLayout.visibility = View.GONE
                recyclerViewTopArtistas.visibility = View.VISIBLE
                tvVerMaisTop.visibility = View.VISIBLE

                recyclerViewTopArtistas.adapter = context?.let { TopArtistaAdapter(response, it) }
            }
        }
    }

    override fun onErrorTop(error: VolleyError) {
        activity?.runOnUiThread {
            binding.apply {
                loadingLayout.visibility = View.GONE
                Log.e("Error Volley","error")
            }
        }
    }

    override fun onErrorJSONTop(error: JSONException) {
        activity?.runOnUiThread {
            binding.apply {
                loadingLayout.visibility = View.GONE
                Log.e("Error JSON","error")
            }
        }
    }

    override fun onSucessGeneros(response: List<TopArtistasDTO>) {
        activity?.runOnUiThread {
            binding.apply {

                rvGeneros.adapter = context?.let { GenerosMomentosAdapter(response, it) }
            }
        }
    }

    override fun onErrorGeneros(error: VolleyError) {
        Log.e("onError","Generos")
    }

    override fun onErrorJSONGeneros(error: JSONException) {
        Log.e("onErrorJSON","Generos")
    }

    override fun onSucessPaises(response: List<PaisesDTO>) {
        activity?.runOnUiThread {
            binding.apply {
                rvPaises.adapter = context?.let { PaisesAdapter(response, it) }
            }
        }
    }

    override fun onErrorPaises(error: VolleyError) {
        Log.e("onError","Paises")
    }

    override fun onErrorJSONPaises(error: JSONException) {
        Log.e("onErrorJSON","Paises")
    }

    override fun onSucessBanners(response: List<BannersDTO>) {
        activity?.runOnUiThread {
            binding.apply {
                tab.visibility = View.VISIBLE
                tab.setupWithViewPager(vp,true)
                vp.adapter = context?.let { BannersAdapter(response, it) }
                vp.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                        Log.i("","")
                    }

                    override fun onPageSelected(position: Int) {
                        positionBanner = position
                    }

                    override fun onPageScrollStateChanged(state: Int) {
                        Log.i("","")
                    }

                })
                mudarBanners()
            }
        }
    }

    override fun onErrorBanners(error: VolleyError) {
        Log.e("onError","Banners")
    }

    override fun onErrorJSONBanners(error: JSONException) {
        Log.e("onErrorJSON","Banners")
    }
}