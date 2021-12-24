package com.orbital.para_voce.dto

import java.io.Serializable


class TocadasDTO(nomeMachine:String, textoMachine:String,idPlaylistArray:List<String>,
                 imageArtista:String,nomeArtista:String,imageAlbum:String,nomeAlbum:String,
                 artistaAlbum:String,imagePlaylist:String,nomePlaylist:String,instagramPlaylist:String,
                 twitterPlaylist:String,idPlaylist:String,autorPlaylist:String,categoria:String):Serializable {
    var nomeMachine: String = nomeMachine
    var textoMachine: String = textoMachine


    var idPlaylistArray: List<String> = idPlaylistArray

    var imageArtista: String = imageArtista
    var nomeArtista: String = nomeArtista

    var imageAlbum: String = imageAlbum
    var nomeAlbum: String = nomeAlbum
    var artistaAlbum: String = artistaAlbum

    var imagePlaylist: String = imagePlaylist
    var nomePlaylist: String = nomePlaylist
    var instagramPlaylist: String = instagramPlaylist
    var twitterPlaylist: String = twitterPlaylist
    var idPlaylist: String = idPlaylist
    var autorPlaylist: String = autorPlaylist
    var categoria: String = categoria
}