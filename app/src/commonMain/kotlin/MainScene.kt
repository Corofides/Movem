import Constants
import Commands.*
import Enums.*
import entities.*

import com.soywiz.klock.*
import com.soywiz.korev.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korim.font.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*
import com.soywiz.korio.lang.*
import com.soywiz.korma.geom.Point

import helpers.*
import models.*

import singletons.*

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
        val level = 1
        val anchorCount = 1

        val backgroundBitmap = resourcesVfs["Pieces/Background/Background.png"].readBitmapSlice()
        val blockBitmap = resourcesVfs["Pieces/Block/Block [T].png"].readBitmapSlice()
        val blockOccupiedBitmap = resourcesVfs["Pieces/Block/Block [Light Up] [T].png"].readBitmapSlice()
        val holderBitmap = resourcesVfs["Pieces/Holder/Holder [T].png"].readBitmapSlice()
        val wallBitmap = resourcesVfs["Pieces/Wall/Wall [No Sides] [T].png"].readBitmapSlice()

        val hudBitmap = resourcesVfs["hud.png"].readBitmapSlice()

//        var levelText  = object {}.javaClass.getResource("levels/Level1.txt")?.readText()
        val levelText = resourcesVfs["levels/Level1.json"].read().toString(Charsets.UTF8)
        println("--------------------")
        println(levelText)
        println("--------------------")
        val levelParser = LevelParser(levelText)
        val levelData = levelParser.parseLevel()

        val rowCount = 20
        val colCount = 20
        val cellWidth = Constants.TILE_SIZE // 32
        val cellHeight = Constants.TILE_SIZE // 32
        val levelGrid = LevelGrid(cellWidth, cellHeight, rowCount, colCount)

        for (i in 1..1024) {
            floor(backgroundBitmap) {
                position(32 * ((i - 1) % 32), 32 * ((i - 1) / 32))
            }
        }

        var player: Player? = null;

        levelData.forEach {

            when (it.tileType) {
                TileType.ANCHOR -> {
                    holder(holderBitmap) { position(levelGrid.getCellPosition(it.x, it.y)) }
                }
                TileType.WALL -> {
                    wall(wallBitmap) { position(levelGrid.getCellPosition(it.x, it.y)) }
                }
                TileType.PLASMACONTAINER -> {
                    block(blockBitmap, blockOccupiedBitmap) {
                        position(levelGrid.getCellPosition(it.x, it.y))
                        addFixedUpdater(30.timesPerSecond) {
                            this.movementUpdateCycle()
                        }
                    }
                }
                TileType.FLOORTILE -> {
                    floor(backgroundBitmap) { position(levelGrid.getCellPosition(it.x, it.y)) }
                }
                TileType.DROID -> {
                    player = player(playerSprites) { position(levelGrid.getCellPosition(it.x, it.y)) }
                }
                TileType.EMPTY -> {
                    // Ignore
                }
                else -> {}
            }
        }

        val font = TtfFont(resourcesVfs["Movem.ttf"].readAll())
        val fontsize = 32.0

        val hud: Hud = hud(hudBitmap, level) {
            position(0.0, 0.0) // 643 x 44
        }

        EventManager.add(hud)

        hud.alignBottomToBottomOf(this)

        hud.addFixedUpdater(30.timesPerSecond) {
            hud.paint(moves, font)
        }

        player?.let {
            addFixedUpdater(30.timesPerSecond) {
                it.movementUpdateCycle()

                var newCommand: MoveCommand? = null

                if (input.keys[Key.LEFT]) {
                    newCommand = MoveCommand(it, Direction.WEST)
                    //commandList.add(MoveCommand(player, Direction.WEST))
                } else if (input.keys[Key.RIGHT]) {
                    newCommand = MoveCommand(it, Direction.EAST)
                    //commandList.add(MoveCommand(player, Direction.EAST))
                } else if (input.keys[Key.UP]) {
                    newCommand = MoveCommand(it, Direction.NORTH)
                    //commandList.add(MoveCommand(player, Direction.NORTH))
                } else if (input.keys[Key.DOWN]) {
                    newCommand = MoveCommand(it, Direction.SOUTH)
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

}
