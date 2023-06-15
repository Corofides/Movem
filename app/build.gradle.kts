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
    version = "0.0.1"
//    exeBaseName = "app"
    name = "Movem"
    description = "Movem Game"
//    orientation = Orientation.DEFAULT
//    copyright = "Copyright (c) 2023 Unknown"
//
//    // Configuring the author
//    authorName = "Corofides"
//    authorEmail = "unknown@unknown"
//    authorHref = "https://github.com/Corofides"
//    author("name", "email", "href")
//
//    authorName = "Alex Hedley"
//    authorEmail = "unknown@unknown"
//    authorHref = "https://github.com/AlexHedley"
//    author("name", "email", "href")
//
    icon = File(rootDir, "src/commonMain/resources/Icon.png")
//
    gameCategory = GameCategory.ARCADE
//    fullscreen = true
//    backgroundColor = 0xff000000.toInt()
//    appleDevelopmentTeamId = java.lang.System.getenv("DEVELOPMENT_TEAM") ?: project.findProperty("appleDevelopmentTeamId")?.toString()
//    appleOrganizationName = "User Name Name"
//    entryPoint = "main"
//    jvmMainClassName = "MainKt"
//    androidMinSdk = null
//
//    //androidAppendBuildGradle("...code...")
//    config("MYPROP", "MYVALUE")
//
//    // Korge Plugins
//    plugin("com.soywiz:korge-admob:$korgeVersion", mapOf("ADMOB_APP_ID" to ADMOB_APP_ID))
//    admob(ADMOB_APP_ID) // Shortcut for admob

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
