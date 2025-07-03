// GameState.kt
package netcode

/**
 * Speichert vergangene Spielzustände mit Zeitstempel für Lag Compensation / Rewind
 */
data class GameState(
    val timestamp: Long,
    val paddle1Y: Int,
    val paddle2Y: Int,
    val ballX: Int,
    val ballY: Int
) {
    /**
     * Hilfsfunktion zum Kopieren mit neuen Werten.
     * Nutzt Kotlin-eigene copy-Funktion mit benannten Parametern.
     */
    fun copyWith(
        paddle1Y: Int = this.paddle1Y,
        paddle2Y: Int = this.paddle2Y,
        ballX: Int = this.ballX,
        ballY: Int = this.ballY,
        timestamp: Long = this.timestamp
    ): GameState {
        return this.copy(
            timestamp = timestamp,
            paddle1Y = paddle1Y,
            paddle2Y = paddle2Y,
            ballX = ballX,
            ballY = ballY
        )
    }
}

/**
 * Historie der vergangenen Spielzustände, begrenzt auf maxSize Einträge
 */
class StateHistory(private val maxSize: Int = 50) {
    private val states = ArrayDeque<GameState>()

    /**
     * Speichert neuen Zustand. Entfernt älteste Zustände bei Überschreitung von maxSize.
     */
    fun recordState(state: GameState) {
        if (states.size >= maxSize) {
            states.removeFirst()
        }
        states.addLast(state)
    }

    /**
     * Sucht den letzten Zustand vor oder gleich dem gegebenen timestamp.
     * Gibt null zurück, wenn keiner gefunden wurde.
     */
    fun getStateAtOrBefore(timestamp: Long): GameState? {
        return states.reversed().find { it.timestamp <= timestamp }
    }

    /**
     * Entfernt alle Zustände bis (inklusive) zu gegebenem timestamp.
     */
    fun removeUpTo(timestamp: Long) {
        while (states.isNotEmpty() && states.first().timestamp <= timestamp) {
            states.removeFirst()
        }
    }

    /**
     * Gibt den aktuell letzten Zustand zurück oder null, falls leer.
     */
    fun latestState(): GameState? = states.lastOrNull()

    /**
     * Für Debugging: Anzahl gespeicherter Zustände
     */
    fun size() = states.size
}
