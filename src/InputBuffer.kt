// Speichert Client-Inputs mit Zeitstempel in einer Warteschlange

data class TimedInput(
    val timestamp: Long,   // z.B. System.currentTimeMillis()
    val input: String      // z.B. "up", "down"
)

class InputBuffer {
    private val inputs = ArrayDeque<TimedInput>()

    // Füge neuen Input hinzu
    fun addInput(input: TimedInput) {
        inputs.addLast(input)
        // Optional: begrenze Größe
        if (inputs.size > 100) {
            inputs.removeFirst()
        }
    }

    // Alle Inputs ab einem bestimmten Zeitpunkt holen
    fun getInputsFrom(timestamp: Long): List<TimedInput> {
        return inputs.filter { it.timestamp >= timestamp }
    }

    // Älteste Inputs entfernen (z.B. wenn verarbeitet)
    fun removeUpTo(timestamp: Long) {
        while (inputs.isNotEmpty() && inputs.first().timestamp <= timestamp) {
            inputs.removeFirst()
        }
    }

    // Für Debug/Status
    fun size() = inputs.size
}
