package com.orbital.machine.model

import androidx.annotation.Nullable
import com.orbital.machine.present.contract.IMachineMainContract

abstract class MachineMainPresenter<VIEW:IMachineMainContract.IMachineMainView>(private var view:IMachineMainContract.IMachineMainView) {

    @Nullable
    open fun getView():IMachineMainContract.IMachineMainView{

        return view as VIEW
    }
}