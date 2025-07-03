import java.io.PrintWriter
import java.net.ServerSocket
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.Executors

fun main() {
    val server = ServerSocket(12345)
    println("✅ Server gestartet auf Port 12345")
    GameLoop.start()

    val pool = Executors.newCachedThreadPool()

    while (true) {
        val client = server.accept()
        println("🔌 Client verbunden: ${client.inetAddress}")
        val out = PrintWriter(client.getOutputStream(), true)
        GameLoop.clients.add(out)

        pool.execute {
            val input = client.getInputStream().bufferedReader()
            while (true) {
                val msg = input.readLine() ?: break
                println("🕹 Eingabe: $msg")

                when {
                    msg.contains("up1") -> {
                        GameLoop.state.paddle1Y -= 10
                        println("⬆️ Paddle 1 hoch → ${GameLoop.state.paddle1Y}")
                    }
                    msg.contains("down1") -> {
                        GameLoop.state.paddle1Y += 10
                        println("⬇️ Paddle 1 runter → ${GameLoop.state.paddle1Y}")
                    }
                    msg.contains("up2") -> {
                        GameLoop.state.paddle2Y -= 10
                        println("⬆️ Paddle 2 hoch → ${GameLoop.state.paddle2Y}")
                    }
                    msg.contains("down2") -> {
                        GameLoop.state.paddle2Y += 10
                        println("⬇️ Paddle 2 runter → ${GameLoop.state.paddle2Y}")
                    }
                }
            }

            println("❌ Client getrennt: ${client.inetAddress}")
            GameLoop.clients.remove(out)
        }
    }
}