package com.orbital.orbital.view.adapter

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.orbital.top.view.fragment.TopFragment

class ViewPagerAdapter(@NonNull private var fm:FragmentManager):
    FragmentPagerAdapter(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private var NUMBER_PAGES:Int= 5

    override fun getCount(): Int {
        return NUMBER_PAGES
    }

    override fun getItem(position: Int): Fragment {

        if(position == 2){
            return TopFragment()
        }

        return Fragment()

    }
}