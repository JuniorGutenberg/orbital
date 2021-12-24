package com.orbital.artistas.service.imple.async

import android.os.AsyncTask
import android.util.Log
import com.orbital.artistas.dto.AlbumDTO
import com.orbital.core.dto.CallBack
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

class ArtistasRelacionadosAsync(private var url:String, private var callBack: CallBack<List<AlbumDTO>,IOException>):AsyncTask<String,String,List<AlbumDTO>>() {

    var items:MutableList<AlbumDTO> = arrayListOf()
    var artistas:MutableList<String> = arrayListOf()
    private var line:String = ""
    private var image:String = ""
    private var name:String = ""
    private var jsonObject:JSONObject = JSONObject()



    override fun doInBackground(vararg p0: String?): List<AlbumDTO> {

        val okHttpClient = OkHttpClient()
        val request: Request = Request.Builder()
            .url(url)
            .build()
        var responses: okhttp3.Response? = null
        try {
            responses = okHttpClient.newCall(request).execute()
        } catch (e: IOException) {
            e.printStackTrace()
            callBack.onError(e)
            return items
        }
        try {
            if (responses.body != null) {
                line = responses.body!!.string()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            callBack.onError(e)
            return items
        }
        try {
            if (line.isNotEmpty()) {
                jsonObject = JSONObject(line)
                try {
                    val similarartists = jsonObject.getJSONObject("similarartists")
                    val artist = similarartists.getJSONArray("artist")

                    val tam:Int = if (artist.length() < 10) {
                        artist.length()
                    } else {
                       10
                    }
                    for (i in 0 until tam) {
                        Log.d("TEXTMEBACK", i.toString())
                        val resultArtist = artist.getJSONObject(i)
                        name = resultArtist.getString("name")
                        artistas.add(name)
                    }
                    setImages(artistas)

                } catch (e: JSONException) {
                    e.printStackTrace()
                    callBack.onErrorJSON(e)
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            callBack.onErrorJSON(e)
        }

        return items
    }

    private fun setImages(artista: List<String>) {

        val thread = Thread {
            for(i in 0 until artista.size){
                try {
                    var doc: Document? = null
                    var artistaPesquisa = artista[i]
                    doc = Jsoup.connect("https://www.last.fm/music/$artistaPesquisa/+images").get()
                    val elementsByTag: org.jsoup.select.Elements = doc.getElementsByTag("meta")
                    for ((i2, element) in elementsByTag.withIndex()) {
                        if (i2 == 9) {
                            if (!element.attr("content").contains("2a96cbd8b46e442fc41c2b86b821562f")) {
                                image = element.attr("content")
                            }
                        }
                    }
                    if (artistaPesquisa != "" && image != "") {
                        if (!image.contains("2a96cbd8b46e442fc41c2b86b821562f")) {
                            items.add(AlbumDTO(image,artistaPesquisa))
                            callBack.onSucess(items)
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        thread.start()
    }
}