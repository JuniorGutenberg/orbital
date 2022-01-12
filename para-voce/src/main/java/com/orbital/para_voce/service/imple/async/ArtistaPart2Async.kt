package com.orbital.para_voce.service.imple.async

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.android.volley.VolleyError
import com.orbital.para_voce.dto.ArtistaDTO
import com.orbital.para_voce.dto.CallBack
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException
import java.util.*

class ArtistaPart2Async(private var context: Context, private var items: List<String>, private var callBack: CallBack<List<ArtistaDTO>,VolleyError>):
    AsyncTask<String,String,List<ArtistaDTO>>() {

    private var itemsArtistas:MutableList<ArtistaDTO> = arrayListOf()
    private var line:String=""
    private var name:String=""
    private var image:String=""
    private var jsonObject:JSONObject = JSONObject()

    override fun doInBackground(vararg p0: String?): List<ArtistaDTO> {
        Collections.shuffle(items)
        for(i in 0 until items.size){
            var s1: String = items[i]
            s1 = s1.replace("-", "+")
            s1 = s1.replace("#", "")
            val okHttpClient = OkHttpClient()
            val request: Request = Request.Builder()
                .url("http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=$s1&api_key=1af5ba4e64c1bdb88199b41e109e6ecf&format=json")
                .build()
            var responses: Response? = null
            try {
                responses = okHttpClient.newCall(request).execute()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                if (responses != null) {
                    if (responses.body != null) {
                        line = responses.body!!.string()
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                if (line != "") {
                    jsonObject =
                        JSONObject(line.substring(line.indexOf("{"), line.lastIndexOf("}") + 1))
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            try {
                val artist: JSONObject = jsonObject.getJSONObject("artist")
                name = artist.getString("name")
                setImages(name)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return itemsArtistas
    }
    private fun setImages(artista: String?) {

        val thread = Thread {
            try {
                var doc: Document? = null
                doc = Jsoup.connect("https://www.last.fm/music/$artista/+images").get()
                val elementsByTag: org.jsoup.select.Elements = doc.getElementsByTag("meta")
                for ((i2, element) in elementsByTag.withIndex()) {
                    if (i2 == 9) {
                        if (!element.attr("content").contains("2a96cbd8b46e442fc41c2b86b821562f")) {
                            image = element.attr("content")
                        }
                    }
                }
                if (artista != null) {
                    if (artista != "" && image != "") {
                        if (!image.contains("2a96cbd8b46e442fc41c2b86b821562f")) {
                            itemsArtistas.add(ArtistaDTO(artista,image))
                            callBack.onSucess(itemsArtistas)
                            Log.e("setImage","Artistas")

                        }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        thread.start()
    }
}