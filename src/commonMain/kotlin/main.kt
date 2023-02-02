import com.soywiz.korge.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.color.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*

/**
 * main
 */
suspend fun main() = Korge(width = 512, height = 512, bgcolor = Colors["#2b2b2b"]) {
	val sceneContainer = sceneContainer()
	sceneContainer.changeTo({ MainScene() })

//    val bitmapEmpty = Bitmap32(32, 32).slice()
//    val bitmapCross = resourcesVfs["cross.png"].readBitmapSlice()
//    val bitmapCircle = resourcesVfs["circle.png"].readBitmapSlice()
//    addChild(resourcesVfs["board.ktree"].readKTree(views))
//    changeCell(0, 1, bitmapCross)
//    this["row0"]["cell1"].alpha(0.5)
}

//fun Container.changeCell(row: Int, column: Int, bitmap: BmpSlice) {
//    val image = this["row$row"]["cell$column"].firstOrNull as? Image?
//    image?.bitmap = bitmap
//}

