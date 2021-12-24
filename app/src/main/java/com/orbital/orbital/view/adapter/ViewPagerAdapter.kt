package com.orbital.orbital.view.adapter

import androidx.annotation.NonNull
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.orbital.biblioteca.view.fragment.BibliotecaFragment
import com.orbital.buscar.view.fragment.BuscarFragment
import com.orbital.para_voce.view.fragment.ParaVoceFragment
import com.orbital.perfil.view.fragment.PerfilFragment
import com.orbital.top.view.fragment.TopFragment

class ViewPagerAdapter(@NonNull private var fm:FragmentManager, private var toolbar: Toolbar):
    FragmentPagerAdapter(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private var NUMBER_PAGES:Int= 5

    override fun getCount(): Int {
        return NUMBER_PAGES
    }

    override fun getItem(position: Int): Fragment {

        if(position == 2){
            return TopFragment(toolbar)
        }
        if(position == 1){
            return BibliotecaFragment()
        }
        if(position == 0){
            return PerfilFragment()
        }
        if(position == 3){
            return ParaVoceFragment(toolbar)
        }
        if(position == 4){
            return BuscarFragment(toolbar)
        }
        return Fragment()

    }
}