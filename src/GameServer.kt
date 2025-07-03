// src/GameServer.kt
import java.net.ServerSocket
import java.io.PrintWriter
import java.util.*

fun main() {
    val server = ServerSocket(12345)
    println("Server läuft auf Port 12345...")

    while (true) {
        val client = server.accept()
        println("Client verbunden: ${client.inetAddress}")
        val out = PrintWriter(client.getOutputStream(), true)

        Timer().scheduleAtFixedRate(object : TimerTask() {
            var x = 0
            override fun run() {
                x += 5
                out.println("{\"ballX\":$x}")
            }
        }, 0, 100)
    }
}
