import Commands.*
import Entities.*
import com.soywiz.klock.*
import com.soywiz.korev.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*
import Enums.*

/**
 * Main Scene
 */
class MainScene : Scene() {
    /**
     * Scene Main
     */
    override suspend fun SContainer.sceneMain() {

        val playerSprites = arrayOf(
            // North
            resourcesVfs["Pieces/Man/Man [Up] [T].png"].readBitmapSlice(),
            // East
            resourcesVfs["Pieces/Man/Man [Right] [T].png"].readBitmapSlice(),
            // South
            resourcesVfs["Pieces/Man/Man [Down] [T].png"].readBitmapSlice(),
            // West
            resourcesVfs["Pieces/Man/Man [Left] [T].png"].readBitmapSlice(),
        )

        val backgroundBitmap = resourcesVfs["Pieces/Background/Background.png"].readBitmapSlice()
        val blockBitmap = resourcesVfs["Pieces/Block/Block [T].png"].readBitmapSlice();
        image(backgroundBitmap)

        val player: Player = player(playerSprites) {
            position(256, 256)
        }

        val block: Block = block(blockBitmap) {
            position(256 + 32, 256);
        }

        val commandList: MutableList<Command> = mutableListOf();
        var commandPosition = 0;

        player.addFixedUpdater(30.timesPerSecond) {
            if (input.keys[Key.LEFT]) {
                commandList.add(MoveCommand(player, Direction.WEST));
            } else if (input.keys[Key.RIGHT]) {
                commandList.add(MoveCommand(player, Direction.EAST));
            } else if (input.keys[Key.UP]) {
                commandList.add(MoveCommand(player, Direction.NORTH));
            } else if (input.keys[Key.DOWN]) {
                commandList.add(MoveCommand(player, Direction.SOUTH));
            }

            for (i in commandPosition until(commandList.size)) {
                commandList[i].exec();
            }

            commandPosition = commandList.size;

        }
    }
}
