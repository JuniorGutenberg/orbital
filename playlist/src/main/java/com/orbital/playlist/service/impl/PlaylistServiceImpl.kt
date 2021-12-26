package com.orbital.playlist.service.impl

import android.app.Activity
import android.os.AsyncTask
import android.util.Log
import com.orbital.core.dto.CallBack
import com.orbital.core.service.BaseService
import com.orbital.playlist.dto.OnlineDTO
import com.orbital.playlist.service.PlaylistService
import com.orbital.playlist.service.impl.async.PlaylistAsync
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL

class PlaylistServiceImpl:BaseService(),PlaylistService {
    override fun getSongs(
        id: String,
        activity: Activity,
        callBack: CallBack<List<OnlineDTO>, IOException>
    ) {
        val idArray = id.split(",")

        for(i in idArray.indices){
            val thread = Thread{
                try {
                    val url = URL(getUrl(idArray[i]))
                    val httpConn = url.openConnection()
                    httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393")

                    val input = BufferedReader(InputStreamReader(httpConn.inputStream))
                    val iterator = input.lineSequence().iterator()


                    while (iterator.hasNext()) {
                        var line = iterator.next()

                        if (line.contains("ytInitialData = {")) {
                            try{
                                if(line.contains("\"playlistVideoListRenderer\":{\"co")){
                                    line = line.substring(line.indexOf("\"playlistVideoListRenderer\":{\"co") + 28)
                                    line = line.substring(0, line.indexOf("}]}}}]}}],\"playlistId\":"))+"}]}}}]}}]}"
                                    PlaylistAsync(line, callBack).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                                }

                            }catch (e: Exception){
                                Log.e("Error Lines:",e.toString())
                            }
                        }
                    }
                    input.close()

                }catch (e: MalformedURLException) {
                    e.printStackTrace();
                } catch  (e: IOException) {
                    e.printStackTrace();
                }
            }
            thread.start()
        }
    }
    private fun getUrl(id: String):String{
        val idUrl = id.replace(" ","%20")
        return "https://www.youtube.com/playlist?list=$idUrl"
    }
}