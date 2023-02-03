import com.soywiz.korev.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*

/**
 * Main Scene
 */
class MainScene : Scene() {
    /**
     * Scene Main
     */
    override suspend fun SContainer.sceneMain() {
        val manUpBitmap = resourcesVfs["Pieces/Man/Man [Up] [T].png"].readBitmapSlice()
        val manDownBitmap = resourcesVfs["Pieces/Man/Man [Down] [T].png"].readBitmapSlice()
        val manLeftBitmap = resourcesVfs["Pieces/Man/Man [Left] [T].png"].readBitmapSlice()
        val manRightBitmap = resourcesVfs["Pieces/Man/Man [Right] [T].png"].readBitmapSlice()

        val backgroundBitmap = resourcesVfs["Pieces/Background/Background.png"].readBitmapSlice()
        image(backgroundBitmap)

        val player: Player = player(manUpBitmap) {
            position(256, 256)
        }

        player.addUpdater {
            if (input.keys[Key.LEFT]) {
                player.changeCell(manLeftBitmap);
                x -= 1
            }
            if (input.keys[Key.RIGHT]) {
                player.changeCell(manRightBitmap);
                x += 1
            }
            if (input.keys[Key.UP]) {
                player.changeCell(manUpBitmap)
                y -= 1
            }
            if (input.keys[Key.DOWN]) {
                player.changeCell(manDownBitmap)
                y += 1
            }
        }
    }
}
