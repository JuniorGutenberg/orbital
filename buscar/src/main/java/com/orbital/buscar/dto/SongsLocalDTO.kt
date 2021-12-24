package com.orbital.buscar.dto

import java.io.Serializable

class SongsLocalDTO(id: Long?,titulo: String?,artista: String?,bytes: ByteArray?,
                    year: String?,local: String?,albumName: String?,numberIdentificacao: Int?,duration: String?,
                    size: String?,bookMaker: String?,generos: String?,albumID: String?,nomeArquivo: String?):Serializable {

     val id: Long? = id
     val titulo: String? = titulo
     val artista: String? = artista
     val bytes: ByteArray? = bytes
     val year: String? = year
     val local: String? = local
     val albumName: String? = albumName

     val numberIdentificacao:Int? = numberIdentificacao
     val duration: String? = duration
     val size: String? = size
     val bookMaker: String? = bookMaker
     val generos: String? = generos


     val albumID: String? = albumID
     val nomeArquivo: String? = nomeArquivo
}