package com.demo.clientapplication.socket

//import io.ktor.http.*
//import io.ktor.server.application.*
//import io.ktor.server.engine.*
//import io.ktor.server.netty.*
//import io.ktor.server.response.*
//import io.ktor.server.routing.*

// Проверка работы клиент-сервер
// Файл HttpServer нужно запустить на стороне сервера (файл HttpServer писал на Java)
// После запуска, можно из приложения отправлять http-запросы на сервер
private const val jsonResponse = """{
    "id": 1,
    "task": "Pay water bill",
    "description": "Pay water bill today"
}"""

fun main() {
    startServer()
}

private fun startServer() {
//    embeddedServer(Netty, 1111) {
//        routing {
//            get("/") {
//                call.respondText("Hello!", ContentType.Text.Plain)
//                println("Path / is OK")
//            }
//            get("/home") {
//                call.respondText("Ktor, you are at home!", ContentType.Text.Plain)
//                println("Path /home is OK")
//            }
//            get("/todo") {
//                call.respondText(jsonResponse, ContentType.Application.Json)
//                println("Connection is OK")
//            }
//            post("/hello") {
//                call.respondText("Ktor, post is OK!", ContentType.Text.Plain)
//                println("Post hello is OK")
//            }
//        }
//    }.start(wait = true)
}