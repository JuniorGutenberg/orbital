package com.orbital.buscar.view.async

import android.os.AsyncTask
import android.util.Log
import com.orbital.core.factory.ByPassCertificated
import java.lang.Exception
import java.util.ArrayList

class AutoCompletAsync(private var url:String): AsyncTask<List<String?>,List<String>,List<String?>>() {

    private fun resul(json: String?): List<String?> {
        var json = json
        val list: MutableList<String?> = ArrayList()
        try {
            json = ByPassCertificated.decodeArray(json)?.get(1)
            val array: Array<String?>? = ByPassCertificated.decodeArray(json)

            if (array?.size!! >= 2) {
                for (i in array.indices) {
                    if (array[i] != null && array[i]!!.isNotEmpty()) {
                        list.add(array[i])
                    }
                }
            }
        } catch (e: Exception) {
            e.message?.let { Log.e("Error Certificated: ", it) };
        }
        return list
    }
    override fun doInBackground(vararg p0: List<String?>): List<String?> {
        try {
            val urlService: String = url
            val bypass = ByPassCertificated.getInstance(true)
            val json = bypass.sendGET(urlService, null)
            if (json.isNotEmpty()) {
                return resul(json)
            }
            return arrayListOf()
        } catch (e: Exception) {
            return arrayListOf()
        }
    }
    override fun onPostExecute(result: List<String?>) {
        super.onPostExecute(result)
    }
}