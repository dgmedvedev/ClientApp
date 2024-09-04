package com.demo.clientapplication.socket

import java.io.DataOutputStream
import java.io.IOException
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