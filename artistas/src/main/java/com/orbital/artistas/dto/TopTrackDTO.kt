package com.orbital.artistas.dto

import java.io.Serializable
import java.math.BigInteger

class TopTrackDTO (image:Int?, name:String?, channelId:String?, ytId:String?,
                   thumbnail:String?, sView:String?, views: BigInteger?, url:String?, canal:String?):
    Serializable {
    val image:Int? = image
    val name: String? = name
    val channelId: String? = channelId
    val ytId: String? = ytId
    val thumbnail: String? = thumbnail
    val sView: String? = sView
    val views: BigInteger? = views
    val url: String? = url
    val canal: String? = canal

}