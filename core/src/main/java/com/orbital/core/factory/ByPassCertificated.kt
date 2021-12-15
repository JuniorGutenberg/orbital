package com.orbital.core.factory

import android.annotation.SuppressLint
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.DataOutputStream
import java.io.IOException
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.*
import javax.net.ssl.*

class ByPassCertificated private constructor() {
    private var enableAllCertificates = false
    @SuppressLint("TrulyRandom")
    private fun enableAllCertificates() {
        try {
            val trustManager: X509TrustManager = object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return emptyArray()
                }
            }
            val trustAllCerts = arrayOf<TrustManager>(trustManager)
            val sc = SSLContext.getInstance("SSL")
            sc.init(null, trustAllCerts, SecureRandom())
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)
            val allHostsValid =
                HostnameVerifier { _, _ -> true }
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(MalformedURLException::class, IOException::class)
    private fun getURLConnection(urlStr: String, isGET: Boolean): HttpURLConnection {
        if (enableAllCertificates) enableAllCertificates()
        val url = URL(urlStr)
        val conn = url.openConnection() as HttpURLConnection
        conn.instanceFollowRedirects = true
        conn.connectTimeout = 1000 * 60
        conn.readTimeout = 1000 * 60
        conn.useCaches = false
        conn.doInput = true
        if (isGET) conn.requestMethod = "GET" else {
            conn.doOutput = true
            conn.requestMethod = "POST"
        }
        return conn
    }

    private fun addHeaders(
        conn: HttpURLConnection?,
        params: Hashtable<String?, String>?
    ): HttpURLConnection? {
        if (conn != null && params != null && params.size > 0) {
            try {
                val keys = params.keys()
                while (keys.hasMoreElements()) {
                    val key = keys.nextElement()
                    val value = params[key]
                    if (key != null && value != null) conn.setRequestProperty(key, value)
                }
            } catch (e: Exception) {
                Log.e("Exception", e.toString())
            }
        }
        return conn
    }

    private fun addPostVars(conn: HttpURLConnection, params: Hashtable<String?, String>) {
        try {
            val method = conn.requestMethod
            if (method == "POST") {
                var urlParameters = ""
                val keys = params.keys()
                while (keys.hasMoreElements()) {
                    val key = URLEncoder.encode(keys.nextElement(), "UTF-8")
                    val value = URLEncoder.encode(params[key], "UTF-8")
                    if (key != null && value != null) {
                        urlParameters += if (urlParameters.length == 0) "$key=$value" else "&$key=$value"
                    }
                }
                if (urlParameters.length > 0) {
                    val wr = DataOutputStream(conn.outputStream)
                    wr.writeBytes(urlParameters)
                    wr.flush()
                    wr.close()
                }
            }
        } catch (e: Exception) {
        }
    }

    private fun addPostJson(conn: HttpURLConnection, json: String, useUrlEncode: Boolean) {
        try {
            val method = conn.requestMethod
            if (method == "POST") {
                val wr = DataOutputStream(conn.outputStream)
                if (useUrlEncode) wr.writeBytes(
                    URLEncoder.encode(
                        json,
                        "UTF-8"
                    )
                ) else wr.writeBytes(json)
                wr.flush()
                wr.close()
            }
        } catch (e: Exception) {
        }
    }

    @Throws(MalformedURLException::class, IOException::class)
    private fun getTextContent(conn: HttpURLConnection): String {
        val content = StringBuilder()
        val code = conn.responseCode
        if (code == 200) {
            val `is` = conn.inputStream
            val bytes = ByteArray(1024)
            var numRead = 0
            while (`is`.read(bytes).also { numRead = it } >= 0) {
                content.append(String(bytes, 0, numRead))
            }
        }
        return content.toString()
    }

    /*
     * This method allows to send a request type JSON
     *
     * Params:
     * Ulr: url of service
     * Headers: headers with the request will be made
     * Json: body json for send
     * */
    fun sendJSON(url: String, headers: Hashtable<String?, String>?, json: String): String {
        var content = ""
        try {
            if (json.length > 0) {
                val conn = getURLConnection(url, false)
                if (headers != null && headers.size > 0) addHeaders(conn, headers)
                addPostJson(conn, json, false)
                content = getTextContent(conn)
            }
        } catch (e: Exception) {
            content = ""
        }
        return content
    }

    /*
     * This method allows to send a request type POST
     *
     * Params:
     * Ulr: url of service
     * Headers: headers with the request will be made
     * params: parameters that will be sent by POST
     * */
    fun sendPOST(
        url: String,
        headers: Hashtable<String?, String>?,
        params: Hashtable<String?, String>
    ): String {
        var content = ""
        try {
            if (params.size > 0) {
                val conn = getURLConnection(url, false)
                if (headers != null && headers.size > 0) addHeaders(conn, headers)
                addPostVars(conn, params)
                content = getTextContent(conn)
            }
        } catch (e: Exception) {
            content = ""
        }
        return content
    }

    /*
     * This method allows to send a request type GET
     *
     * Params:
     * Ulr: url of service
     * Headers: headers with the request will be made
     * */
    fun sendGET(url: String, headers: Hashtable<String?, String>?): String {
        var content = ""
        content = try {
            val conn = getURLConnection(url, true)
            if (headers != null && headers.size > 0) addHeaders(conn, headers)
            getTextContent(conn)
        } catch (e: Exception) {
            ""
        }
        return content
    }

    companion object {
        /*
     * Crea una instancia de la clase
     *
     * Params:
     * enableAllCertificates: para conexiones por https true activa el bypass con certificados no validos
     * */
        fun getInstance(enableAllCertificates: Boolean): ByPassCertificated {
            val cls = ByPassCertificated()
            cls.enableAllCertificates = enableAllCertificates
            return cls
        }

        /*
     * This method lets you create a url with parameters such GET
     *
     * Params:
     * Ulr: url of service
     * Params: headers with the request will be made
     * */
        fun encodeUrl(url: String?, params: Hashtable<String?, String?>): String? {
            var outUrl = url
            try {
                var urlParameters = ""
                val keys = params.keys()
                while (keys.hasMoreElements()) {
                    val key = URLEncoder.encode(keys.nextElement(), "UTF-8")
                    val value = URLEncoder.encode(params[key], "UTF-8")
                    if (key != null && value != null) {
                        urlParameters += if (urlParameters.length == 0) "?$key=$value" else "&$key=$value"
                    }
                }
                if (outUrl != null && urlParameters.length > 0) outUrl += urlParameters
            } catch (e: Exception) {
            }
            return outUrl
        }

        //***************************************************************************
        fun decodeArray(source: String?): Array<String?>? {
            try {
                val o = JSONArray(source)
                val length = o.length()
                if (length > 0) {
                    val array = arrayOfNulls<String>(length)
                    for (i in 0 until length) {
                        array[i] = o.getString(i)
                    }
                    return array
                }
            } catch (e: Exception) {
            }
            return null
        }

        fun decodeHashtable(source: String?): Hashtable<String, Any> {
            val table = Hashtable<String, Any>(0)
            try {
                val o = JSONObject(source)
                val length = o.length()
                if (length > 0) {
                    val en = o.keys()
                    while (en.hasNext()) {
                        val key = en.next().toString()
                        val value = o[key].toString() + ""
                        table[key] = value
                    }
                }
            } catch (e: Exception) {
            }
            return table
        }
    }
}