package com.orbital.buscar.model

import androidx.annotation.Nullable
import com.orbital.buscar.present.contract.ISongsLocalContract

abstract class SongsLocalPresenter<VIEW:ISongsLocalContract.ISongsLocalView>
    (private var view:ISongsLocalContract.ISongsLocalView) {

        @Nullable
        open fun getView():VIEW{
            return view as VIEW
        }
}