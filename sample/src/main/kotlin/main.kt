/*
 * Developed by Ji Sungbin, 2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/jisungbin/KotlinDataClassNoCopy/blob/main/LICENSE
 */

@file:Suppress("DataClassPrivateConstructor", "LocalVariableName")

@NoCopy
data class Adult private constructor(
    val age: Int,
    val name: String,
) {
    companion object {
        fun from(name: String): Adult {
            return if (name == "Zohn") {
                Adult(age = 49, name = "Zohn")
            } else {
                error("No one allowed except \"Zohn\".")
            }
        }
    }
}

fun main() {
    val Zosh = Adult.from("Zohn").copy(age = 21, name = "Zosh")
    println(Zosh.toString())
}
