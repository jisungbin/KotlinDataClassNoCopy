# KotlinDataClassNoCopy

> Suppress the generation of the `copy()` function in Kotlin's `data class`.

Kotlin's `data class` is a really cool feature that automatically generates useful functions like `equals`, `hashCode`, `copy`, and `componentN`.

However, sometimes you want all the benefits of the `data class` but not the `copy()` function.

For example, code like this:

```kotlin
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
```

This `data class` only accepts the name "Zohn", but the auto-generated `copy()` function allows you to bypass this condition and create a new instance.

```kotlin
val Zosh = Adult.from("Zohn").copy(age = 21, name = "Zosh")
```

With this library, the `copy()` function of `data class` is no longer accessible.

```
Unresolved reference: copy
```

---

## Download ![maven-central](https://img.shields.io/maven-central/v/land.sungbin.kotlin.dataclass.nocopy/kotlin-dataclass-nocopy-gradle)

```gradle
plugins {
    id("land.sungbin.kotlin.dataclass.nocopy.plugin") version "$version"
}
```

## Usage

Apply the `@NoCopy` annotation to `data class`.

```kotlin
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
```

You can configure plugin with properties on the `nocopy` extension.

```kotlin
nocopy {
  // Whether NoCopy is enabled
  enabled.set(true) // default
  
  // Whether to show verbose logging
  verbose.set(false) // default
}
```

## Caveats

- Interaction with IDEs is not yet supported. (there is no public API to handle this)
- This library uses the Kotlin Compiler Plugin, which is still unstable.

## License

This project is licensed under the MIT License. Please refer to the [LICENSE file](LICENSE) for details.
