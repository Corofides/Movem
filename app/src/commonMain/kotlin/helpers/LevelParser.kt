package helpers

import com.soywiz.korio.file.std.*
import com.soywiz.korio.lang.*
import com.soywiz.korio.serialization.json.*

class LevelParser(levelText: String) {
    private val levelText = levelText

//    fun getLevelText() {
//        val levelText = resourcesVfs["levels/Level1.txt"].read().toString(Charsets.UTF8)
//        println("--------------------")
//        println(levelText)
//        println("--------------------")
//    }

//    {
//        "tiles": [
//        [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1]
//        ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1]
//        ,[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1]
//        ,[0,0,0,0,0,1,1,1,1,1,1,1,1,1,0,1,0,0,1]
//        ,[1,1,1,1,1,1,0,0,0,0,0,0,0,1,1,1,0,0,1]
//        ,[1,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,1]
//        ,[1,0,0,0,0,0,0,1,1,1,0,0,1,1,1,0,0,0,1]
//        ,[1,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,1]
//        ,[1,0,0,1,1,1,1,1,1,1,1,1,1,0,1,0,0,0,1]
//        ,[1,0,0,0,1,0,0,0,0,0,0,0,0,0,1,1,1,1,1]
//        ,[1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
//        ,[1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
//        ,[1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
//        ],
//        "boxes": [
//        [16,7],[5,6],[16,6],[6,5],[10,5],[11,5],[16,5],[16,4]
//        ],
//        "goals": [
//        [1,11],[2,11],[3,11],[1,10],[2,10],[3,10],[1,9],[1,8]
//        ],
//        "start": [16,3]
//    }

    // NSString *pattern = @"(?s)(\\w+):\\s*\\[(.*?)\\](?=,\\r?\\n\\s*\\w+:|$)";

    fun parseLevel() {
        //val jsonObject: JsonObject = Json.decodeFromString(jsonString)
        var levelJson = Json.parse(levelText)
        levelJson.
    }


    // https://www.baeldung.com/kotlin/regular-expressions
    val regex = """a([bc]+)d?""".toRegex()
    var matchResult = regex.find("abcb abbd")

    fun getTiles() {
//        "LevelData(tiles: [])"
    }

    fun getBoxes() {
//        "boxes: []"
    }

    fun getGoals() {
//        "goals: []"
    }

    fun getStart() {
//        ""
    }

}
