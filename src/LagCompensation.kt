package netcode

/**
 * Führt Lag Compensation durch:
 * 1. Findet den letzten Zustand vor dem neuen Input-Zeitstempel (rewind)
 * 2. Wendet neuen Input an
 * 3. Spielt alle späteren Inputs ab (reconciliation)
 * 4. Speichert den neuen Zustand
 */
fun applyLagCompensation(
    stateHistory: StateHistory,
    inputBuffer: InputBuffer,
    newInput: TimedInput
): GameState {
    // Schritt 1: Rewind - Zustand zum Inputzeitpunkt suchen
    val baseState = stateHistory.getStateAtOrBefore(newInput.timestamp)
        ?: stateHistory.latestState()
        ?: error("Kein Basiszustand gefunden!")

    var currentState = baseState.copy()

    // Hilfsfunktion, die Input auf Zustand anwendet
    fun applyInputToState(state: GameState, input: TimedInput): GameState {
        val paddleSpeed = 5
        var newPaddle1Y = state.paddle1Y

        when (input.input) {
            "up" -> newPaddle1Y -= paddleSpeed
            "down" -> newPaddle1Y += paddleSpeed
            else -> { /* keine Änderung */ }
        }

        // Spielfeldgrenzen (z.B. 0 bis 100)
        newPaddle1Y = newPaddle1Y.coerceIn(0, 100)

        return state.copyWith(paddle1Y = newPaddle1Y, timestamp = input.timestamp)
    }

    // Schritt 2: Neuen Input anwenden
    currentState = applyInputToState(currentState, newInput)

    // Schritt 3: Spätere Inputs abspielen
    val laterInputs = inputBuffer.getInputsFrom(newInput.timestamp + 1)
    for (input in laterInputs) {
        currentState = applyInputToState(currentState, input)
    }

    // Schritt 4: Neuen Zustand speichern
    stateHistory.recordState(currentState)

    // Verarbeitete Inputs entfernen
    inputBuffer.removeUpTo(newInput.timestamp)

    return currentState
}