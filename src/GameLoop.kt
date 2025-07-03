// GameLoop.kt
import java.util.*
import java.io.PrintWriter
import java.util.concurrent.CopyOnWriteArrayList

object GameLoop {
    val clients = CopyOnWriteArrayList<PrintWriter>()
    val state = GameState()

    // Spielfeld & Objekte
    val canvasWidth = 750
    val canvasHeight = 585
    val ballRadius = 10
    val paddleWidth = 10
    val paddleHeight = 80
    val paddle1X = 20
    val paddle2X = canvasWidth - 20 - paddleWidth // = 720

    fun start() {
        println("🎮 GameLoop gestartet")

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                // Ball bewegen
                state.ballX += state.ballVelX
                state.ballY += state.ballVelY

                // Wand oben/unten
                if (state.ballY <= ballRadius || state.ballY >= canvasHeight - ballRadius) {
                    state.ballVelY *= -1
                    println("⬆️⬇️ Ball bounced oben/unten")
                }

                // Paddle 1 (links)
                if (state.ballX - ballRadius <= paddle1X + paddleWidth &&
                    state.ballY >= state.paddle1Y &&
                    state.ballY <= state.paddle1Y + paddleHeight &&
                    state.ballVelX < 0 // Ball kommt von rechts
                ) {
                    state.ballVelX *= -1
                    println("🎯 Ball bounced an Paddle 1")
                }

                // Paddle 2 (rechts)
                if (state.ballX + ballRadius >= paddle2X &&
                    state.ballY >= state.paddle2Y &&
                    state.ballY <= state.paddle2Y + paddleHeight &&
                    state.ballVelX > 0 // Ball kommt von links
                ) {
                    state.ballVelX *= -1
                    println("🎯 Ball bounced an Paddle 2")
                }

                // OUT OF BOUNDS rechts/links (optional Punkt)
                if (state.ballX < -ballRadius || state.ballX > canvasWidth + ballRadius) {
                    println("💀 Ball verloren – Reset")
                    resetBall()
                }

                // Debug
                println("📤 Tick → Ball: (${state.ballX}, ${state.ballY}) | P1: ${state.paddle1Y} | P2: ${state.paddle2Y}")

                // JSON senden
                val json = """{
  "ballX": ${state.ballX},
  "ballY": ${state.ballY},
  "p1": ${state.paddle1Y},
  "p2": ${state.paddle2Y}
}"""
                for (client in clients) {
                    client.println(json)
                }
            }
        }, 0, 100)
    }

    fun resetBall() {
        state.ballX = canvasWidth / 2
        state.ballY = canvasHeight / 2
        state.ballVelX = if ((0..1).random() == 0) 4 else -4
        state.ballVelY = if ((0..1).random() == 0) 4 else -4
    }
}
