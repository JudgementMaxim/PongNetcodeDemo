// GameLoop.kt
// Verwaltet den Spielablauf: Ball bewegen, Spielstand aktualisieren, Daten an alle Clients senden

import java.util.*
import java.io.PrintWriter
import java.util.concurrent.CopyOnWriteArrayList

object GameLoop {
    // Liste aller verbundenen Clients, an die der Zustand gesendet wird
    val clients = CopyOnWriteArrayList<PrintWriter>()

    // Der zentrale Spielzustand, von allen geteilt
    val state = GameState()

    fun start() {
        // Spiel-Timer (Ticker): wird alle 100 Millisekunden ausgelöst (10x pro Sekunde)
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                // Ballposition updaten
                state.ballX += state.ballVelX
                state.ballY += state.ballVelY

                // Ball bounct oben/unten
                if (state.ballY <= 0 || state.ballY >= 400) state.ballVelY *= -1

                // Ball bounct links/rechts (noch keine Punktelogik)
                if (state.ballX <= 0 || state.ballX >= 600) state.ballVelX *= -1

                // Spielstand als JSON für den Client serialisieren
                val json = """{
                    "ballX": ${state.ballX},
                    "ballY": ${state.ballY},
                    "p1": ${state.paddle1Y},
                    "p2": ${state.paddle2Y}
                }"""

                // An alle verbundenen Clients senden
                for (client in clients) {
                    client.println(json)
                }
            }
        }, 0, 100) // Startverzögerung = 0ms, Intervall = 100ms
    }
}
