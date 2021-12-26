package com.orbital.top.model

import androidx.annotation.Nullable
import com.orbital.top.presenter.contract.IGenerosContract

abstract class GenerosPresenter<VIEW:IGenerosContract.IGenerosView>(private var view: IGenerosContract.IGenerosView) {
    @Nullable
    open fun getView():IGenerosContract.IGenerosView{
        return view as VIEW
    }
}