package com.orbital.buscar.service.impl

import android.app.Activity
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import com.orbital.buscar.dto.*
import com.orbital.buscar.service.SongsLocalService
import com.orbital.buscar.view.async.OnlineAsync
import com.orbital.buscar.view.async.RelacionadosAsync
import com.orbital.core.service.BaseService
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL
import java.text.DecimalFormat

class SongsLocalImple:BaseService(),SongsLocalService {
    override fun getSongs(key:String,activity: Activity, callback: CallbackSongsLocal<List<SongsLocalDTO>>) {
        val items:MutableList<SongsLocalDTO> = arrayListOf()
        var mKey = key
        var number = 0
        val thread = Thread{
            val contentResolver = activity.contentResolver
            val uri:Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            if(mKey.contains("\'")){
                mKey = mKey.replace("'","")
            }
            val selectionMimeType = (MediaStore.Files.FileColumns.MIME_TYPE + "=?"
                    + " AND " + MediaStore.Audio.Media.IS_MUSIC + " != 0"
                    + " AND " + MediaStore.Audio.Media.ALBUM + " != 'WhatsApp Audio'"
                    + " AND " + MediaStore.Audio.Media.TITLE + " LIKE '%"+mKey+"%'"
                    )
            val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension("mp3")
            val selectionArgsMp3 = arrayOf(mimeType)
            val mCursor: Cursor? = contentResolver.query(uri, null, selectionMimeType, selectionArgsMp3, null)

            if(mCursor!=null && mCursor.moveToFirst()){
                val albumColuna = mCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
                val yearColuna = mCursor.getColumnIndex(MediaStore.Audio.Media.YEAR)
                val durationColuna = mCursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
                val dataColuna = mCursor.getColumnIndex(MediaStore.Audio.Media.DATA)
                val bookMakerC = mCursor.getColumnIndex(MediaStore.Audio.Media.BOOKMARK)
                val albumName = mCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
                val tituloColuna = mCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
                val idColuna = mCursor.getColumnIndex(MediaStore.Audio.Media._ID)
                val artistaColuna = mCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)

                do {
                    val bookMaker = mCursor.getString(bookMakerC)
                    val thisDuration = mCursor.getLong(durationColuna)
                    val thisData = mCursor.getString(dataColuna)
                    val fileSize = File(thisData)
                    val fileLenght = fileSize.length()
                    val fileKB = fileLenght / 1024.0
                    val fileMB = fileKB / 1024.0
                    val decimalFormat = DecimalFormat("0.00")
                    val thisSize = decimalFormat.format(fileMB) + "MB"
                    val thisYear = mCursor.getString(yearColuna)
                    val thisAlbum = mCursor.getString(albumColuna)
                    val thisAlbumName = mCursor.getString(albumName)
                    val thisId = mCursor.getLong(idColuna)
                    val uriId = MediaStore.Audio.Genres.getContentUriForAudioId(
                        "external",
                        thisId.toInt()
                    )
                    val nameArquivo = File(thisData).name
                    val thisTitulo = mCursor.getString(tituloColuna)
                    var thisArtista = mCursor.getString(artistaColuna)
                    if (thisArtista == null) {
                        thisArtista = "Artista Desconhecido"
                    }

                    val mCursorGeneros = contentResolver.query(uriId,
                        arrayOf(MediaStore.Audio.Genres._ID, MediaStore.Audio.Genres.NAME),null,null,null)


                    var genero = ""


                    if (mCursorGeneros != null) {
                        if(mCursorGeneros.moveToFirst()){
                            val n = mCursorGeneros.getColumnIndex(MediaStore.Audio.Genres.NAME)
                            genero = mCursorGeneros.getString(n)
                            if(genero == ""){
                                genero = "Desconhecido"
                            }
                        }
                    }
                    items.add(SongsLocalDTO(thisId,thisTitulo,thisArtista,byteArrayOf(),thisYear,thisData,thisAlbumName,
                        number,convertDuration(thisDuration),thisSize,bookMaker,genero,thisAlbum,nameArquivo))
                    number++
                    mCursorGeneros?.close()

                }while (mCursor.moveToNext())
                callback.onSucess(items)
            }else{
                mCursor?.close()
                callback.onError()
            }
            mCursor?.close()

        }
        thread.start()
    }
    override fun getSongsOnline(
        key: String,
        activity: Activity,
        calllback: CallbackOnline<List<OnlineDTO>, IOException>
    ) {
        val thread = Thread{
            try {
                val url = URL(getUrl(key))
                val httpConn = url.openConnection()
                httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393")

                val input = BufferedReader(InputStreamReader(httpConn.inputStream))
                val iterator = input.lineSequence().iterator()


                while (iterator.hasNext()) {
                    var line = iterator.next()

                    if (line.contains("ytInitialData = {")) {
                        line = line.substring(line.indexOf("tents\":[{\"vi") + 7)
                        line = line.substring(0, line.indexOf("}]}}],\"trackingParams\""))+"}]}}]"
                        OnlineAsync(line,calllback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
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

    override fun getSongsRelacionados(
        key: String,
        activity: Activity,
        calllback: CallbackOnline<List<OnlineDTO>, IOException>
    ) {
        val thread = Thread{
            try {
                val url = URL(getUrlRelacionados(key))
                val httpConn = url.openConnection()
                httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393")

                val input = BufferedReader(InputStreamReader(httpConn.inputStream))
                val iterator = input.lineSequence().iterator()


                while (iterator.hasNext()) {
                    var line = iterator.next()

                    if (line.contains(",{\"itemSectionRenderer\":{\"contents\":")) {
                        line = line.substring(line.indexOf("results\":[{\"compact") + 10)
                        line = line.substring(0, line.indexOf(",{\"continuationItemRenderer\""))
                        RelacionadosAsync(line,calllback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
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

    override fun getArtista(
        key: String,
        activity: Activity,
        callback: CallbackOnline<List<ArtistaDTO>, IOException>
    ) {
        var image = ""
        val itemsArtistas:MutableList<ArtistaDTO> = arrayListOf()
        val thread = Thread {
            try {
                var doc: Document? = null
                doc = Jsoup.connect("https://www.last.fm/music/$key/+images").get()
                val elementsByTag: org.jsoup.select.Elements = doc.getElementsByTag("meta")
                for ((i2, element) in elementsByTag.withIndex()) {
                    if (i2 == 9) {
                        if (!element.attr("content").contains("2a96cbd8b46e442fc41c2b86b821562f")) {
                            image = element.attr("content")
                        }
                    }
                }
                if (image != "") {
                    if (!image.contains("2a96cbd8b46e442fc41c2b86b821562f")) {
                        itemsArtistas.add(ArtistaDTO(key,image))
                        callback.onSucess(itemsArtistas)
                    }
                }

            } catch (e: IOException) {
                e.printStackTrace()
                callback.onError(e)
            }
        }
        thread.start()
    }

    private fun getUrl(key:String):String{
        val url = "https://www.youtube.com/results?search_query=$key&sp=EgIQAQ%253D%253D"
        return url.replace(" ","%20")
    }
    private fun getUrlRelacionados(key:String):String{
        val url = "https://www.youtube.com/watch?v=$key"
        return url.replace(" ","%20")
    }
    private fun getUrlArtista(key:String):String{
        val url = "http://ws.audioscrobbler.com/2.0/?method=artist.search&artist=$key&api_key=1af5ba4e64c1bdb88199b41e109e6ecf&format=json"
        return url.replace("#","")
    }
    private fun convertDuration(duration: Long): String {
        var out = ""
        val hours: Long = try {
            duration / 3600000
        } catch (e: Exception) {
            e.printStackTrace()
            return out
        }
        val remainingMinutes = (duration - hours * 3600000) / 60000
        var minutes = remainingMinutes.toString()
        if (minutes == "0") {
            minutes = "00"
        }
        val remainingSeconds = duration - hours * 3600000 - remainingMinutes * 60000
        var seconds = remainingSeconds.toString()
        seconds = if (seconds.length < 2) {
            "00"
        } else {
            seconds.substring(0, 2)
        }
        if (hours > 0) {
            out = "$hours:$minutes:$seconds"
        } else {
            out = "$minutes:$seconds"
        }
        return out
    }
}