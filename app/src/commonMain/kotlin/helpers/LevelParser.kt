package helpers

import Enums.*
import com.soywiz.korio.serialization.json.*

import models.*

class LevelParser(private val levelText: String) {

    fun parseLevel(): List<Tile> {
        val levelJson = levelText.fromJson() as ArrayList<ArrayList<String>>
        println(levelJson)

        val tiles = mutableListOf<Tile>()

        levelJson.forEachIndexed { row, rowData ->
            rowData.forEachIndexed { col, cell ->
                println(cell)
                val tileType = TileType from cell
                val tile: Tile = Tile(tileType!!, col, row)
                tiles.add(tile)
            }
        }

        println(tiles)
        return tiles
    }

}
