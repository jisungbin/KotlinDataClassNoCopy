/*
 * Developed by Ji Sungbin, 2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/jisungbin/KotlinDataClassNoCopy/blob/main/LICENSE
 */

internal inline fun <T> Iterable<T>.indexedAll(
    predicate: (index: Int, element: T) -> Boolean,
): Boolean {
    if (this is Collection && isEmpty()) return true
    for ((index, element) in withIndex()) {
        if (!predicate(index, element)) return false
    }
    return true
}