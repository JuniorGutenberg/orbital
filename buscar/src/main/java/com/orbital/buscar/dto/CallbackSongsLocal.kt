package com.orbital.buscar.dto

interface CallbackSongsLocal<DTO_SUCESS> {
    fun onSucess(response:DTO_SUCESS)
    fun onError()
}