package com.demo.clientapplication.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.InetSocketAddress
import java.net.Socket
import java.net.URL

class MainViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String> = _response

    init {
        connectToServer()
    }

    fun onHttpRequestButtonPressed() {
        viewModelScope.launch {
            var connection: HttpURLConnection? = null
            try {
                val content = withContext(Dispatchers.IO) {
                    val url = URL(SERVER_URL)
                    connection = url.openConnection() as HttpURLConnection
                    connection?.requestMethod = REQUEST_GET
                    connection?.connectTimeout = CONNECTION_TIMEOUT
                    connection?.setRequestProperty("Content-Type", "application/json")

                    val inputStream = connection?.inputStream
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    var inputLine: String?
                    val response = StringBuilder()
                    while (reader.readLine().also { inputLine = it } != null) {
                        response.append(inputLine).append('\n')
                    }
                    reader.close()
                    response.toString()
                }
                _response.value = "HTTP connection is OK.\n $content"
            } catch (ioe: IOException) {
                ioe.printStackTrace()
                _response.value =
                    "HTTP sending error. The server is unavailable\nIOException: ${ioe.message}"
            } finally {
                connection?.disconnect()
            }
        }
    }

    fun onTcpRequestButtonPressed() {
        viewModelScope.launch {
            try {
                val receivedData = getData()
                _response.value = "Received Data: connection №$receivedData"
            } catch (ioe: IOException) {
                ioe.printStackTrace()
                _response.value =
                    "Sending error. The TCP server is unavailable\nIOException: ${ioe.message}"
            }
        }
    }

    private suspend fun getData(): String {
        return withContext(Dispatchers.IO) {
            val clientSocket = Socket()
            val socketAddress = InetSocketAddress(SERVER_HOST, SERVER_TCP_PORT)
            clientSocket.connect(socketAddress, CONNECTION_TIMEOUT)
            val inputStream = clientSocket.getInputStream()
            val br = BufferedReader(InputStreamReader(inputStream))
            val receivedData = br.readLine()
            receivedData
        }
    }

    private fun connectToServer() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val clientSocket = Socket()
                    val socketAddress = InetSocketAddress(SERVER_HOST, SERVER_TCP_PORT)
                    clientSocket.connect(socketAddress, CONNECTION_TIMEOUT)
                    val outputStream = clientSocket.getOutputStream()
                    val writer = OutputStreamWriter(outputStream)
                    writer.write("ClientApp launched")
                    writer.flush()
                    clientSocket.close()
                }
                _response.value = "Connection to the TCP server is OK"
            } catch (ioe: IOException) {
                ioe.printStackTrace()
                _response.value =
                    "TCP server connection failure.\n" +
                            "IOException: ${ioe.message}"
            }
        }
    }

    companion object {
        private const val CONNECTION_TIMEOUT = 1000
        private const val REQUEST_GET = "GET"
        private const val SERVER_HOST = "172.16.16.32"
        private const val SERVER_HTTP_PORT = 1111
        private const val SERVER_TCP_PORT = 3333
        private const val SERVER_URL = "http://$SERVER_HOST:$SERVER_HTTP_PORT/todo"
    }
}