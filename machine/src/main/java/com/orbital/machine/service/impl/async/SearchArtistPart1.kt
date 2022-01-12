package com.orbital.machine.service.impl.async

import android.os.AsyncTask
import com.orbital.machine.dto.ArtistaDTO
import com.orbital.machine.dto.callback.CallbackNumber
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

class SearchArtistPart1(private var url:String, private var callBack: CallbackNumber<List<ArtistaDTO>, IOException>):
    AsyncTask<String,String,String>() {

    var items:MutableList<ArtistaDTO> = arrayListOf()
    private var line = ""
    private var jsonObject = JSONObject()
    private var image:String = ""
    private var name:String = ""
    var artistas:MutableList<String> = arrayListOf()


    override fun doInBackground(vararg p0: String?): String {
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
            return ""
        }
        try {
            if (responses.body != null) {
                line = responses.body!!.string()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            callBack.onError(e)
            return ""
        }
        try {
            if (line.isNotEmpty()) {
                jsonObject = JSONObject(line)
                try {

                    val results = jsonObject.getJSONObject("results")
                    val artistmatches = results.getJSONObject("artistmatches")
                    val artist = artistmatches.getJSONArray("artist")

                    val tam = if(artist.length()>=20){
                        20
                    }else{
                        artist.length()
                    }

                    for (i in 0 until tam) {
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

        return ""
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
                            items.add(ArtistaDTO(artistaPesquisa,image))
                            callBack.onSucess(items,artista.size)
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