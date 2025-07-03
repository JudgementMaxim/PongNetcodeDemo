// GameState.kt
// Repräsentiert den zentralen Spielzustand, den alle Clients sehen sollen

data class GameState(
    var ballX: Int = 300,       // X-Position des Balls
    var ballY: Int = 200,       // Y-Position des Balls
    var ballVelX: Int = 4,      // X-Geschwindigkeit des Balls
    var ballVelY: Int = 4,      // Y-Geschwindigkeit des Balls

    var paddle1Y: Int = 200,    // Y-Position von Spieler 1 (linkes Paddle)
    var paddle2Y: Int = 200     // Y-Position von Spieler 2 (rechtes Paddle)
)
