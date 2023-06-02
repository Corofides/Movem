import com.soywiz.korge.gradle.*

plugins {
	alias(libs.plugins.korge)
    id("org.jetbrains.dokka") version "1.7.20"
    id("org.jetbrains.kotlinx.kover") version "0.6.1"
//    kotlin("plugin.serialization") version "1.8.21"
////    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.21"
}

korge {
	id = "com.example.movem"
// To enable all targets at once

	//targetAll()

// To enable targets based on properties/environment variables
	//targetDefault()

// To selectively enable targets

	targetJvm()
	targetJs()
	//targetDesktop()
	//targetIos()
	//targetAndroidIndirect() // targetAndroidDirect()
}
