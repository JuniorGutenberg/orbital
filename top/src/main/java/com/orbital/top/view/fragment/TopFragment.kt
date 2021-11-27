package com.orbital.top.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.VolleyError
import com.orbital.top.R
import com.orbital.top.databinding.TopFragmentBinding
import com.orbital.top.di.component.DaggerTopArtistaComponent
import com.orbital.top.di.module.TopArtistaModule
import com.orbital.top.dto.TopArtistasDTO
import com.orbital.top.presenter.TopArtistaPresent
import com.orbital.top.presenter.contract.ITopArtistaContract
import com.orbital.top.view.adapter.TopArtistaAdapter
import com.squareup.picasso.Picasso
import org.json.JSONException
import javax.inject.Inject

class TopFragment:Fragment(), ITopArtistaContract.ITopArtistaView{

    private lateinit var binding: TopFragmentBinding
    @Inject
    lateinit var present: TopArtistaPresent

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
            present.getTopArtista(it)

        }

        return binding.root
    }
    private fun initComponents(){
        binding.apply {
            Picasso.with(context).load(R.drawable.universe_600).fit().centerCrop().into(imageView)

            val linearLayoutManager = LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false
            )

            recyclerViewTopArtistas.layoutManager = linearLayoutManager
            recyclerViewTopArtistas.isNestedScrollingEnabled = false
            recyclerViewTopArtistas.setHasFixedSize(true)
        }
    }

    override fun onSucess(response: List<TopArtistasDTO>) {
        activity?.runOnUiThread {
            binding.apply {
                loadingLayout.visibility = View.GONE
                recyclerViewTopArtistas.visibility = View.VISIBLE
                tvVerMaisTop.visibility = View.VISIBLE

                recyclerViewTopArtistas.adapter = context?.let { TopArtistaAdapter(response, it) }
            }
        }
    }

    override fun onError(error: VolleyError) {
        activity?.runOnUiThread {
            binding.apply {
                loadingLayout.visibility = View.GONE
                Log.e("Error Volley","error")
            }
        }
    }

    override fun onErrorJSON(error: JSONException) {
        activity?.runOnUiThread {
            binding.apply {
                loadingLayout.visibility = View.GONE
                Log.e("Error JSON","error")
            }
        }
    }
}