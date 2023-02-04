import Commands.*
import com.soywiz.klock.*
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

        val commandList: MutableList<Command> = mutableListOf();
        var commandPosition = 0;

        player.addFixedUpdater(30.timesPerSecond) {
            if (input.keys[Key.LEFT]) {
                player.changeCell(manLeftBitmap);
                commandList.add(MoveCommand(player, -1.0, 0.0));
            }
            if (input.keys[Key.RIGHT]) {
                player.changeCell(manRightBitmap);
                commandList.add(MoveCommand(player, 1.0, 0.0));
            }
            if (input.keys[Key.UP]) {
                player.changeCell(manUpBitmap)
                commandList.add(MoveCommand(player, 0.0, -1.0));
            }
            if (input.keys[Key.DOWN]) {
                player.changeCell(manDownBitmap)
                commandList.add(MoveCommand(player, 0.0, 1.0));
            }

            for (i in commandPosition until(commandList.size)) {
                commandList[i].exec();
            }

            commandPosition = commandList.size;

        }
    }
}
