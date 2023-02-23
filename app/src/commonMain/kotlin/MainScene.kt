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

        val backgroundBitmap = resourcesVfs["Pieces/Background/Background.png"].readBitmapSlice()
        val blockBitmap = resourcesVfs["Pieces/Block/Block [T].png"].readBitmapSlice()
        val hudBitmap = resourcesVfs["hud.png"].readBitmapSlice()

//        image(backgroundBitmap)

        val wall: Wall = wall(backgroundBitmap) {
            position(32, 32);
        }

        val player: Player = player(playerSprites) {
            position(256, 256)
        }

        val block: Block = block(blockBitmap) {
            position(256 + 33, 256);
        }

        val font = TtfFont(resourcesVfs["Movem.ttf"].readAll())
        val fontsize = 32.0

        val hud: Hud = hud(hudBitmap) {
            position(0.0, 0.0) // 643 x 44

            // Moves 0000
//            val moves = NativeImage(643, 44).apply {
//                val totalMovesString = commandList.size.toString().padStart(4, '0')
//                totalMovesString.forEachIndexed  { index, element ->
//                    getContext2d().fillText("" + element, x = 97.0 + (32.0 * index), y = 28.0, font, textSize = fontsize, color = Colors.PURPLE)
//                }
//            }
//            image(moves)

            // Pushes 0000
            val pushes = NativeImage(643, 44).apply {
                getContext2d().fillText("0", x = 337.0, y = 28.0, font, textSize = fontsize, color = Colors.PURPLE)
                getContext2d().fillText("0", x = 369.0, y = 28.0, font, textSize = fontsize, color = Colors.PURPLE)
            }
            image(pushes)

            // Level 00
            val level = NativeImage(643, 44).apply {
                getContext2d().fillText("0", x = 577.0, y = 28.0, font, textSize = fontsize, color = Colors.PURPLE)
                getContext2d().fillText("1", x = 609.0, y = 28.0, font, textSize = fontsize, color = Colors.PURPLE)
            }
            image(level)

        }

        hud.alignBottomToBottomOf(this)

        /*
        inline fun fillText(
            text: String,
            x: Number,
            y: Number,
            font: Font? = this.font,
            textSize: Double = this.fontSize,
            halign: HorizontalAlign = this.horizontalAlign,
            valign: VerticalAlign = this.verticalAlign,
            color: Paint? = null
        )
         */

        // https://docs.korge.org/korge/reference/font/
//        val font = TtfFont(resourcesVfs["Movem.ttf"].readAll())
//        val bitmap = NativeImage(643, 512).apply {
//            getContext2d().fillText("HELLO WORLD", x = 100.0, y = 100.0, font, textSize = 100.0, color = Colors.RED)
//        }
//        image(bitmap)



        hud.addFixedUpdater(30.timesPerSecond) {

            hud.paint(commandList.size, font)

        }

        player.addFixedUpdater(30.timesPerSecond) {

            player.movementUpdateCycle();

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
