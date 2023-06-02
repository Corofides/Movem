package helpers

import com.soywiz.korgw.platform.*
import com.soywiz.korio.file.std.*
import com.soywiz.korio.lang.*
import com.soywiz.korio.serialization.json.*

import models.*

class LevelParser(levelText: String) {
    private val levelText = levelText

    fun parseLevel(): LevelData {
        var levelJson = levelText.fromJson() as LinkedHashMap<String, Any?>
        println(levelJson)

        val tiles = levelJson.getValue("tiles") as ArrayList<ArrayList<Int>>
        var boxes = levelJson.getValue("boxes") as ArrayList<ArrayList<Int>>
        var goals = levelJson.getValue("goals") as ArrayList<String>
        var start = levelJson.getValue("start") as ArrayList<Int>
        var levelData = LevelData(tiles, boxes, goals, start)

        return levelData
    }

}
