/*
 * Developed by Ji Sungbin, 2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/jisungbin/KotlinDataClassNoCopy/blob/main/LICENSE
 */

import javax.inject.Inject
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property

abstract class KotlinDataClassNoCopyExtension @Inject constructor(objects: ObjectFactory) {
    /**
     * Whether NoCopy is enabled
     */
    val enabled: Property<Boolean> = objects.property(Boolean::class.javaObjectType).convention(true)

    /**
     * Whether to show verbose logging
     */
    val verbose: Property<Boolean> = objects.property(Boolean::class.javaObjectType).convention(false)
}