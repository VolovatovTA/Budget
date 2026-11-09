package ru.bysoft.android.budget.uikit.colors

/**
 * Maps an entity id (wallet, category) to a palette slot that is the same on every device and
 * after every restart. Uses FNV-1a 32-bit over the UTF-8 bytes rather than [String.hashCode] so
 * the result does not depend on the runtime.
 */
fun stablePaletteIndex(id: String, size: Int): Int {
    require(size > 0) { "palette size must be positive" }
    var hash = FNV_OFFSET_BASIS
    for (byte in id.toByteArray(Charsets.UTF_8)) {
        hash = (hash xor (byte.toInt() and 0xff)) * FNV_PRIME
    }
    return (hash and Int.MAX_VALUE) % size
}

private const val FNV_OFFSET_BASIS = 0x811C9DC5.toInt()
private const val FNV_PRIME = 16777619
