package com.demo.clientapplication.socket

import java.io.BufferedReader
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket

// Проверка работы клиент-сервер
// Файл ServerApp нужно запустить на стороне сервера
// После запуска, можно из приложения отправлять запросы на сервер
fun main() {
    SocketServer.start()
}

class SocketServer {
    companion object {
        private const val SERVER_PORT = 5005

        fun start() {
            try {
                var count = 1
                var clientConn: Socket
                var serverOutput: DataOutputStream
                val server = ServerSocket(SERVER_PORT)

                // Data input
                clientConn = server.accept()
                val serverInput = DataInputStream(clientConn.getInputStream())
                val br = BufferedReader(InputStreamReader(serverInput))
                val readData = br.readLine()
                println(readData)
                clientConn.close()

                // Data output
                while (true) {
                    clientConn = server.accept()
                    serverOutput = DataOutputStream(clientConn.getOutputStream())
                    if (count == 10) break
                    serverOutput.writeBytes("${count++} is completed\n")
                }
                serverOutput.writeBytes("$count is completed. SocketServer is shutdown\n")
                clientConn.close()
            } catch (ioe: IOException) {
                ioe.printStackTrace()
            }
        }
    }
}