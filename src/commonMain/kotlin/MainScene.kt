import com.soywiz.korev.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.*

/**
 * Main Scene
 */
class MainScene : Scene() {
    override suspend fun SContainer.sceneMain() {
        val minDegrees = (-16).degrees
        val maxDegrees = (+16).degrees

        val UP = 0
        val RIGHT = 1
        val DOWN = 3
        val LEFT = 4

        val manUpBitmap = resourcesVfs["Pieces/Man/Man [Up] [T].png"].readBitmapSlice() //.readBitmap()
        val manDownBitmap = resourcesVfs["Pieces/Man/Man [Down] [T].png"].readBitmapSlice() //.readBitmap()

        val player = Player(manUpBitmap);

        player.position(256, 256)
        addChild(player)

//        fun changeCell(player: Player, bitmap: BmpSlice) {
//            player.image?.bitmap = bitmap
//        }

        //image.set

        player.addUpdater {

            if (input.keys[Key.LEFT]) {
                x -= 1
            }

            if (input.keys[Key.RIGHT]) {
                x += 1
            }

            if (input.keys[Key.UP]) {
                player.changeCell(manUpBitmap)
                //image.bitmapSrc = manUpBitmap;
                y -= 1
            }

            if (input.keys[Key.DOWN]) {
                player.changeCell(manDownBitmap)
                y += 1
            }

        }

    }

//    fun Container.changeCell(bitmap: BmpSlice) {
//        val image = this as? Image?
//        image?.bitmap = bitmap
//    }
}
