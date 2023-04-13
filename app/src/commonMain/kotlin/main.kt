import com.soywiz.korge.*
import com.soywiz.korge.scene.*
import com.soywiz.korim.color.*

/**
 * main
 */
suspend fun main() = Korge(width = 643, height = 512, bgcolor = Colors["#2b2b2b"]) {
	val sceneContainer = sceneContainer()
	sceneContainer.changeTo({ MainScene() })
}


