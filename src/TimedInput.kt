package netcode

/**
 * Repräsentiert einen Spielerinput mit Zeitstempel.
 */
data class TimedInput(
    val timestamp: Long,
    val input: String // z.B. "up", "down", "none"
)

/**
 * Puffer für die Inputs eines Spielers.
 */
class InputBuffer {
    private val inputs = mutableListOf<TimedInput>()

    fun addInput(input: TimedInput) {
        inputs.add(input)
        inputs.sortBy { it.timestamp } // Optional: Sortierung sicherstellen
    }

    fun getInputsFrom(timestamp: Long): List<TimedInput> {
        return inputs.filter { it.timestamp >= timestamp }
    }

    fun removeUpTo(timestamp: Long) {
        inputs.removeAll { it.timestamp <= timestamp }
    }
}