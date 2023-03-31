import com.soywiz.klock.*
import com.soywiz.korev.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*
import com.soywiz.korim.font.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.color.*

import Commands.*
import entities.*
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

        val commandList: MutableList<Command> = mutableListOf()
        var commandPosition = 0
        var moves = 0
        var pushes = 0
        var level = 1

        val backgroundBitmap = resourcesVfs["Pieces/Background/Background.png"].readBitmapSlice()
        val blockBitmap = resourcesVfs["Pieces/Block/Block [T].png"].readBitmapSlice()
        val holderBitmap = resourcesVfs["Pieces/Holder/Holder [T].png"].readBitmapSlice()
        val wallBitmap = resourcesVfs["Pieces/Wall/Wall [No Sides] [T].png"].readBitmapSlice()

        val hudBitmap = resourcesVfs["hud.png"].readBitmapSlice()

//        image(backgroundBitmap)

        val floor: Floor = floor(backgroundBitmap) {
            position(32, 32)
        }

        val wall1: Wall = wall(wallBitmap) {
            position(32, 32)
        }

        val wall2: Wall = wall(wallBitmap) {
            position(32, 64)
        }

        var index = 0

        for (i in 1..1024) {
            floor(backgroundBitmap) {
                position(32 * ((i - 1) % 32), 32 * ((i - 1) / 32))
            }
        }

        val player: Player = player(playerSprites) {
            position(256, 256)
        }

        val holder: Holder = holder(holderBitmap) {
            position(128, 128)
        }

        val block: Block = block(blockBitmap) {
            position(256 + 33, 256)
        }

        block.addFixedUpdater(30.timesPerSecond) {
            block.movementUpdateCycle()
        }

        val font = TtfFont(resourcesVfs["Movem.ttf"].readAll())
        val fontsize = 32.0

        val hud: Hud = hud(hudBitmap, level) {
            position(0.0, 0.0) // 643 x 44
        }

        hud.alignBottomToBottomOf(this)

        hud.addFixedUpdater(30.timesPerSecond) {
            hud.paint(moves, pushes, font)
        }

        player.addFixedUpdater(30.timesPerSecond) {
            player.movementUpdateCycle();

            var newCommand : MoveCommand? = null
            
            if (input.keys[Key.LEFT]) {
                newCommand = MoveCommand(player, Direction.WEST);
                //commandList.add(MoveCommand(player, Direction.WEST))
            } else if (input.keys[Key.RIGHT]) {
                newCommand = MoveCommand(player, Direction.EAST);
                //commandList.add(MoveCommand(player, Direction.EAST))
            } else if (input.keys[Key.UP]) {
                newCommand = MoveCommand(player, Direction.NORTH);
                //commandList.add(MoveCommand(player, Direction.NORTH))
            } else if (input.keys[Key.DOWN]) {
                newCommand = MoveCommand(player, Direction.SOUTH);
                //commandList.add(MoveCommand(player, Direction.SOUTH))
            }

            newCommand?.let {
                if (it.exec()) {
                    moves++
                    commandList.add(it)
                }
            }

        }
    }

}
