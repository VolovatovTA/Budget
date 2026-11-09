package ru.bysoft.android.budget.uikit.colors

/**
 * Maps an entity id (wallet, category) to a palette slot. [String.hashCode] is specified by Kotlin
 * identically on every platform, so the slot is the same on each device and after every restart
 * without storing a colour; consecutive ids land in consecutive slots, which keeps a small set of
 * categories visibly distinct.
 */
fun stablePaletteIndex(id: String, size: Int): Int {
    require(size > 0) { "palette size must be positive" }
    return (id.hashCode() and Int.MAX_VALUE) % size
}
