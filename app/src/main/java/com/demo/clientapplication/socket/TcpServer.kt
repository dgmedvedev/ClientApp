package com.demo.clientapplication.socket

import java.io.BufferedReader
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket

// Проверка работы клиент-сервер
// Файл TcpServer нужно запустить на стороне сервера
// После запуска, можно из приложения отправлять tcp-запросы на сервер
fun main() {
    TcpServer.start()
}

class TcpServer {

    companion object {
        private const val SERVER_PORT = 3333
        private val serverSocket = ServerSocket(SERVER_PORT)

        fun start() {
            try {
                var count = 1
                var socket: Socket
                var dataOutputStream: DataOutputStream

                // Data input
                socket = serverSocket.accept()
                val inputStream = socket.getInputStream()
                val dataInputStream = DataInputStream(inputStream)
                val reader = BufferedReader(InputStreamReader(dataInputStream))
                val readerData = reader.readLine()
                println(readerData)
                reader.close()
                dataInputStream.close()
                inputStream.close()
                socket.close()

                // Data output
                while (true) {
                    socket = serverSocket.accept()
                    dataOutputStream = DataOutputStream(socket.getOutputStream())
                    if (count == 10) break
                    dataOutputStream.writeBytes("${count++} is completed\n")
                }
                dataOutputStream.writeBytes("$count is completed. SocketServer is shutdown\n")
                dataOutputStream.close()
                socket.close()
            } catch (ioe: IOException) {
                ioe.printStackTrace()
            } finally {
                serverSocket.close()
            }
        }
    }
}