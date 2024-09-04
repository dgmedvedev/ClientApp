package com.demo.clientapplication.presentation

import android.util.Log
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
import java.net.Socket

class MainViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String> = _response

    fun serverConnect() {
        viewModelScope.launch {
            try {
                val receivedData = getReceivedData()
                _response.value = "Received Data: connection №$receivedData"
                log("Received Data: connection №$receivedData is completed")
            } catch (ioe: IOException) {
                ioe.printStackTrace()
                _response.value = "Connection failure\n" + "IOException: ${ioe.message}"
                log("IOException: ${ioe.message}")
            }
        }
    }

    private suspend fun getReceivedData(): String {
        return withContext(Dispatchers.IO) {
            val clientSocket = Socket(SERVER_HOST, SERVER_PORT)
            val inputStream = clientSocket.getInputStream()
            val br = BufferedReader(InputStreamReader(inputStream))
            val receivedData = br.readLine()
            receivedData
        }
    }

    private fun log(message: String) {
        Log.d("SERVER_TAG", message)
    }

    companion object {
        private const val SERVER_HOST = "172.16.16.32"
        private const val SERVER_PORT = 5005
    }
}