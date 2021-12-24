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
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.ObjectInputStream

class ArtistaPart1Async(private var context: Context, private var callback: CallBack<List<ArtistaDTO>,VolleyError>): AsyncTask<String,String,String>(){

    private var itemsArtista:MutableList<String> = arrayListOf("Anitta","3030","Eminem")
    private var itemsArtistaFinal:MutableList<String> = arrayListOf("Orochi","Filipe Ret","BIN")
    private var line:String=""
    private var name:String=""
    private var jsonObject:JSONObject = JSONObject()

    override fun doInBackground(vararg p0: String?): String {
        itemsArtista.addAll(readPlayListAddYT())
        itemsArtista.addAll(readPlayListAddYT2())
        for (i in 0 until itemsArtista.size) {
            val okHttpClient = OkHttpClient()

            var s1: String = itemsArtista[i]
            s1 = s1.replace("-", "+")
            s1 = s1.replace("#", "")
            val request: Request = Request.Builder()
                .url("http://ws.audioscrobbler.com/2.0/?method=artist.getsimilar&artist=$s1&api_key=1af5ba4e64c1bdb88199b41e109e6ecf&format=json")
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
                if (line.isNotEmpty()) {
                    jsonObject =
                        JSONObject(line.substring(line.indexOf("{"), line.lastIndexOf("}") + 1))
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            try {
                val similarartists = jsonObject.getJSONObject("similarartists")
                val artist = similarartists.getJSONArray("artist")
                val resultArtist = artist.getJSONObject(0)
                name = resultArtist.getString("name")
                if (!itemsArtistaFinal.contains(name)) {
                    itemsArtistaFinal.add(name)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        return ""
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        itemsArtista.addAll(itemsArtistaFinal)

        ArtistaPart2Async(context,itemsArtista, callback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }
    private fun readPlayListAddYT(): List<String> {
        var toReturn: List<String> = arrayListOf()
        val fis: FileInputStream
        try {
            fis = context.openFileInput("ARTISTA_ESCUTADO")
            val oi = ObjectInputStream(fis)
            toReturn = oi.readObject() as List<String>
            oi.close()
        } catch (e: FileNotFoundException) {
            Log.e("InternalStorage", e.message!!)
        } catch (e: IOException) {
            Log.e("InternalStorage", e.message!!)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        return toReturn
    }
    private fun readPlayListAddYT2(): List<String>  {
        var toReturn: List<String> = arrayListOf()
        val fis: FileInputStream
        try {
            fis = context.openFileInput("ArtistasSelecionados")
            val oi = ObjectInputStream(fis)
            toReturn = oi.readObject() as List<String>
            oi.close()
            Log.d("QUANDOEUSAIDOMO", toReturn.size.toString())
        } catch (e: FileNotFoundException) {
            Log.e("InternalStorage", e.message!!)
        } catch (e: IOException) {
            Log.e("InternalStorage", e.message!!)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        return toReturn
    }
}