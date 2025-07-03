// GameServer.kt
// Startet den TCP-Server, akzeptiert Clients und verarbeitet deren Eingaben

import java.io.PrintWriter
import java.net.ServerSocket
import java.util.concurrent.Executors

fun main() {
    // Server öffnet Port 12345 (frei wählbar)
    val server = ServerSocket(12345)
    println("✅ Server gestartet auf Port 12345")

    // Startet die zentrale GameLoop (Ticker + State)
    GameLoop.start()

    // Threadpool zur parallelen Verarbeitung von Clients
    val pool = Executors.newCachedThreadPool()

    while (true) {
        // Wartet auf Verbindung eines neuen Clients
        val client = server.accept()
        println("🔌 Client verbunden: ${client.inetAddress}")

        // Output-Stream (für Rückmeldung an den Client)
        val out = PrintWriter(client.getOutputStream(), true)

        // Client zum Broadcast hinzufügen
        GameLoop.clients.add(out)

        // In einem eigenen Thread: Eingaben des Clients lesen
        pool.execute {
            val input = client.getInputStream().bufferedReader()
            while (true) {
                val msg = input.readLine() ?: break // Bricht bei Verbindungsabbruch

                println("🕹 Eingabe vom Client: $msg")

                // Sehr einfache Steuerungserkennung
                // TODO: Später durch strukturiertes Protokoll ersetzen (z.B. JSON)

                when {
                    msg.contains("up1") -> GameLoop.state.paddle1Y -= 10
                    msg.contains("down1") -> GameLoop.state.paddle1Y += 10
                    msg.contains("up2") -> GameLoop.state.paddle2Y -= 10
                    msg.contains("down2") -> GameLoop.state.paddle2Y += 10
                }
            }

            println("❌ Client getrennt: ${client.inetAddress}")
            GameLoop.clients.remove(out)
        }
    }
}
