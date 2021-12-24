package com.orbital.artistas.service.imple.async

import android.os.AsyncTask
import com.orbital.core.dto.CallBack
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class ListernerAsync(private var url:String, private var callBack: CallBack<String,IOException>): AsyncTask<String, String, String>() {

    private var line:String=""
    private var listerners:String=""
    private var jsonObject:JSONObject = JSONObject()

    override fun doInBackground(vararg p0: String?): String {
        val okHttpClient = OkHttpClient()
        val request: okhttp3.Request = Request.Builder()
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
                    val artist = jsonObject.getJSONObject("artist")
                    val status = artist.getJSONObject("stats")
                    listerners = status.getString("listeners")
                }catch (e:JSONException){
                    e.printStackTrace()
                    callBack.onErrorJSON(e)
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            callBack.onErrorJSON(e)
        }

        return listerners
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        result?.let { callBack.onSucess(it) }
    }
}