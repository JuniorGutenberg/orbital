package com.orbital.top.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.orbital.top.databinding.BannersItemBinding
import com.orbital.top.dto.BannersDTO
import com.squareup.picasso.Picasso

class BannersAdapter(private var items:List<BannersDTO>, private var context: Context):
    PagerAdapter() {
    private var NUMBER_BANNERS=3

    lateinit var binding:BannersItemBinding

    override fun getCount(): Int {
        return NUMBER_BANNERS
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {

        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position:Int): Any {
        binding = BannersItemBinding.inflate(LayoutInflater.from(context))

        binding.apply {
            Picasso.with(context).load(items[position].image).centerCrop().fit().into(iv)
        }

        container.addView(binding.root)

        return binding.root
    }
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout?)
    }
}