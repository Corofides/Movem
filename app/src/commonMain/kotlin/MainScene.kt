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
        val levelText = resourcesVfs["levels/Level1.txt"].read().toString(Charsets.UTF8)
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

        /*{
            index: 0;
            [
                objects
            ]
            // wall, player, movable object
        }*/


        for (i in 1..1024) {
            floor(backgroundBitmap) {
                position(32 * ((i - 1) % 32), 32 * ((i - 1) / 32))
            }
        }

        val player: Player = player(playerSprites) {
            position(544, 320)
        }


        val width = levelData.tiles.size;


        // ArrayList<ArrayList<Int>>
        levelData.tiles.forEachIndexed { row, rowData ->
            val walls = mutableListOf<Wall>()

            rowData.forEachIndexed { col, cell ->
                if (cell == 0) {
                    // Ignore
                } else {
                    walls.add(wall(wallBitmap) { position(levelGrid.getCellPosition(col, row, true, true)) })
                }
            }
        }

        // region Level
//        var walls = listOf(
//            wall(wallBitmap) { position(levelGrid.getCellPosition(1, 1)) },
//            wall(wallBitmap) { position(levelGrid.getCellPosition(1, 2)) },
//            wall(wallBitmap) { position(32, 96) },
//            wall(wallBitmap) { position(32, 128) },
//            wall(wallBitmap) { position(32, 160) },
//            wall(wallBitmap) { position(32, 192) },
//            wall(wallBitmap) { position(32, 224) },
//            wall(wallBitmap) { position(32, 256) },
//            wall(wallBitmap) { position(32, 288) },
//
//            wall(wallBitmap) { position(64, 288) },
//            wall(wallBitmap) { position(96, 288) },
//            wall(wallBitmap) { position(128, 288) },
//            wall(wallBitmap) { position(160, 288) },
//            wall(wallBitmap) { position(192, 288) },
//
//            wall(wallBitmap) { position(192, 320) },
//            wall(wallBitmap) { position(224, 320) },
//            wall(wallBitmap) { position(256, 320) },
//            wall(wallBitmap) { position(288, 320) },
//            wall(wallBitmap) { position(320, 320) },
//            wall(wallBitmap) { position(352, 320) },
//            wall(wallBitmap) { position(384, 320) },
//            wall(wallBitmap) { position(416, 320) },
//            wall(wallBitmap) { position(448, 320) },
//
//            wall(wallBitmap) { position(448, 288) },
//            wall(wallBitmap) { position(480, 288) },
//            wall(wallBitmap) { position(512, 288) },
//
//            wall(wallBitmap) { position(512, 320) },
//            wall(wallBitmap) { position(512, 352) },
//            wall(wallBitmap) { position(512, 384) },
//            wall(wallBitmap) { position(512, 416) },
//
//            wall(wallBitmap) { position(544, 416) },
//            wall(wallBitmap) { position(576, 416) },
//            wall(wallBitmap) { position(608, 416) },
//
//            wall(wallBitmap) { position(608, 384) },
//            wall(wallBitmap) { position(608, 352) },
//            wall(wallBitmap) { position(608, 320) },
//            wall(wallBitmap) { position(608, 288) },
//            wall(wallBitmap) { position(608, 256) },
//            wall(wallBitmap) { position(608, 224) },
//            wall(wallBitmap) { position(608, 192) },
//            wall(wallBitmap) { position(608, 160) },
//            wall(wallBitmap) { position(608, 128) },
//
//            wall(wallBitmap) { position(576, 128) },
//            wall(wallBitmap) { position(512, 128) },
//            wall(wallBitmap) { position(544, 128) },
//            wall(wallBitmap) { position(480, 128) },
//
//            wall(wallBitmap) { position(480, 160) },
//            wall(wallBitmap) { position(480, 192) },
//            wall(wallBitmap) { position(480, 224) },
//
//            wall(wallBitmap) { position(448, 224) },
//            wall(wallBitmap) { position(416, 224) },
//
//            wall(wallBitmap) { position(416, 192) },
//            wall(wallBitmap) { position(416, 160) },
//
//            wall(wallBitmap) { position(384, 160) },
//            wall(wallBitmap) { position(352, 160) },
//            wall(wallBitmap) { position(320, 160) },
//            wall(wallBitmap) { position(288, 160) },
//            wall(wallBitmap) { position(256, 160) },
//            wall(wallBitmap) { position(224, 160) },
//            wall(wallBitmap) { position(192, 160) },
//            wall(wallBitmap) { position(160, 160) },
//            wall(wallBitmap) { position(128, 160) },
//
//            wall(wallBitmap) { position(160, 128) },
//            wall(wallBitmap) { position(160, 96) },
//            wall(wallBitmap) { position(160, 64) },
//            wall(wallBitmap) { position(160, 32) },
//
//            wall(wallBitmap) { position(128, 32) },
//            wall(wallBitmap) { position(96, 32) },
//            wall(wallBitmap) { position(64, 32) },
//
//            // Center
//            wall(wallBitmap) { position(256, 224) },
//            wall(wallBitmap) { position(288, 224) },
//            wall(wallBitmap) { position(320, 224) },
//            wall(wallBitmap) { position(256, 256) },
//            wall(wallBitmap) { position(288, 256) },
//            wall(wallBitmap) { position(320, 256) },
//        )

        var anchors = listOf(
            holder(holderBitmap) { position(64, 64) },
            holder(holderBitmap) { position(96, 64) },
            holder(holderBitmap) { position(128, 64) },
            holder(holderBitmap) { position(64, 96) },
            holder(holderBitmap) { position(96, 96) },
            holder(holderBitmap) { position(128, 96) },
            holder(holderBitmap) { position(64, 128) },
            holder(holderBitmap) { position(64, 160) },
        )

        val blocks = listOf(
            block(blockBitmap, blockOccupiedBitmap) { position(224, 256) },
            block(blockBitmap, blockOccupiedBitmap) { position(188, 224) },
            block(blockBitmap, blockOccupiedBitmap) { position(352, 256) },
            block(blockBitmap, blockOccupiedBitmap) { position(384, 256) },
            block(blockBitmap, blockOccupiedBitmap) { position(544, 256) },
            block(blockBitmap, blockOccupiedBitmap) { position(544, 288) },
            block(blockBitmap, blockOccupiedBitmap) { position(544, 224) },
            block(blockBitmap, blockOccupiedBitmap) { position(544, 192) },
        )
        //endregion

        blocks.forEach {
            it.addFixedUpdater(30.timesPerSecond) {
                it.movementUpdateCycle()
            }
        }

        /*block.addFixedUpdater(30.timesPerSecond) {
            block.movementUpdateCycle()
        }*/

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

        player.addFixedUpdater(30.timesPerSecond) {
            player.movementUpdateCycle()

            var newCommand : MoveCommand? = null
            
            if (input.keys[Key.LEFT]) {
                newCommand = MoveCommand(player, Direction.WEST)
                //commandList.add(MoveCommand(player, Direction.WEST))
            } else if (input.keys[Key.RIGHT]) {
                newCommand = MoveCommand(player, Direction.EAST)
                //commandList.add(MoveCommand(player, Direction.EAST))
            } else if (input.keys[Key.UP]) {
                newCommand = MoveCommand(player, Direction.NORTH)
                //commandList.add(MoveCommand(player, Direction.NORTH))
            } else if (input.keys[Key.DOWN]) {
                newCommand = MoveCommand(player, Direction.SOUTH)
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
