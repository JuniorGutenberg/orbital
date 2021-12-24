package com.orbital.artistas.model

import androidx.annotation.Nullable
import com.orbital.artistas.presenter.contract.IListernerContract

abstract class ListernerPresenter<VIEW: IListernerContract.IListernerView>
    (private var view:IListernerContract.IListernerView) {

        @Nullable
        open fun getView():VIEW{
            return view as VIEW
        }
    }